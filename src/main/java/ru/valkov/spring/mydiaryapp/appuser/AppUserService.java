package ru.valkov.spring.mydiaryapp.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.valkov.spring.mydiaryapp.email.EmailSender;
import ru.valkov.spring.mydiaryapp.email.EmailUtils;
import ru.valkov.spring.mydiaryapp.main.entities.Course;
import ru.valkov.spring.mydiaryapp.registration.token.ConfirmationToken;
import ru.valkov.spring.mydiaryapp.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          ConfirmationTokenService confirmationTokenService, EmailSender emailSender) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
    }

    @Transactional
    public void createNewUser(AppUser appUser) throws IllegalStateException {
        Optional<AppUser> unexpectedUser = appUserRepository.findByUsername(appUser.getUsername());

        String token = UUID.randomUUID().toString();
        String linkToConfirmToken = "http://158.160.109.122:3000/sign-up/confirm?token=" + token;

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        );

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        if (!unexpectedUser.isPresent()) {
            AppUser savedUser = appUserRepository.save(appUser);
            confirmationToken.setAppUser(savedUser);
        } else {
            if (unexpectedUser.get().isEnabled()) {
                throw new IllegalStateException(String.format("User with email %s already exists", appUser.getUsername()));
            } else {
                if (anyTokenNotExpired(unexpectedUser.get())) {
                    throw new IllegalStateException(String.format("User with email %s already exists", appUser.getUsername()));
                } else {
                    confirmationToken.setAppUser(unexpectedUser.get());
                }
            }
        }

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        emailSender.sendEmail(appUser.getUsername(), EmailUtils.buildEmail(linkToConfirmToken));
    }

    private boolean anyTokenNotExpired(AppUser notExpectedUser) {
        List<ConfirmationToken> confirmationTokens = confirmationTokenService.getTokensByUserId(notExpectedUser.getId());
        boolean anyTokenNotExpired = false;
        for (ConfirmationToken token: confirmationTokens) {
            if (token.getExpiresAt().isAfter(LocalDateTime.now())) {
                anyTokenNotExpired = true;
            }
        }
        return anyTokenNotExpired;
    }

    @Transactional
    public void confirmAnAccount(String token) throws IllegalStateException {
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationTokenByToken(token);

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token already expired");
        }

        AppUser appUser = appUserRepository.findById(confirmationToken.getAppUser().getId())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        confirmationTokenService.updateConfirmedAt(confirmationToken.getId(), LocalDateTime.now());

        appUserRepository.updateEnabledById(appUser.getId(), true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException(String.format("User with username %s not found", username)));
    }

    public AppUser saveUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

}
