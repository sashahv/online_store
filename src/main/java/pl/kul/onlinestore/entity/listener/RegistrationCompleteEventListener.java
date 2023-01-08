package pl.kul.onlinestore.entity.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.kul.onlinestore.entity.user.User;
import pl.kul.onlinestore.event.RegistrationCompleteEvent;
import pl.kul.onlinestore.service.UserService;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    @Autowired
    public RegistrationCompleteEventListener(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // stwórz verification token
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        // email -> user
        String url = event.getApplicationUrl()
                + "/verifyRegistration?token="
                + token;

        // sendVerificationEmail();
        log.info("Link do weryfikacji użytkownika: {}"
                , url);
    }
}

