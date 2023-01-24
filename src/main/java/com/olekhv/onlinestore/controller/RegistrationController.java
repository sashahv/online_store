package com.olekhv.onlinestore.controller;

import com.olekhv.onlinestore.service.user.PasswordResetTokenService;
import com.olekhv.onlinestore.service.user.UserService;
import com.olekhv.onlinestore.service.user.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.olekhv.onlinestore.entity.user.PasswordModel;
import com.olekhv.onlinestore.entity.user.User;
import com.olekhv.onlinestore.entity.user.UserModel;
import com.olekhv.onlinestore.entity.user.VerificationToken;
import com.olekhv.onlinestore.event.RegistrationCompleteEvent;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v1/users")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher publisher, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.publisher = publisher;
        this.verificationTokenService = verificationTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return ResponseEntity.ok("Konto zostało stworzone");
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<?> verifyRegistration(@RequestParam("token") String token) {
        String result = verificationTokenService.saveVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return ResponseEntity.ok("User Verified Successfully");
        }
        return ResponseEntity.badRequest().body("Bad user");
    }

    @GetMapping("/resendVerifyToken")
    public ResponseEntity<?> resendVerifyToken(@RequestParam("token") String oldToken,
                                               HttpServletRequest request) {
        VerificationToken verificationToken =
                verificationTokenService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerifyTokenMail(user, applicationUrl(request), verificationToken);
        return ResponseEntity.ok("Link do weryfikacji został wysłany");
    }

    public void resendVerifyTokenMail(User user,
                                      String applicationUrl,
                                      VerificationToken verificationToken) {
        String url = applicationUrl
                + "/api/v1/verifyRegistration?token=" +
                verificationToken.getToken();
        ;

        // sendVerificationEmail();
        log.info("Link do weryfikacji użytkownika: {}"
                , url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

