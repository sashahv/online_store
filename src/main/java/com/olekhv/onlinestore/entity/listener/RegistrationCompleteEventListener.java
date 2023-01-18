package com.olekhv.onlinestore.entity.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.olekhv.onlinestore.entity.user.User;
import com.olekhv.onlinestore.event.RegistrationCompleteEvent;
import com.olekhv.onlinestore.service.user.VerificationTokenService;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenService verificationTokenService;

    @Autowired
    public RegistrationCompleteEventListener(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // stwórz verification token
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.saveVerificationTokenForUser(token, user);
        // email -> user
        String url = event.getApplicationUrl()
                + "/api/v1/verifyRegistration?token="
                + token;

        // sendVerificationEmail();
        log.info("Link do weryfikacji użytkownika: {}"
                , url);
    }
}

