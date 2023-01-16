package pl.kul.onlinestore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kul.onlinestore.entity.user.PasswordModel;
import pl.kul.onlinestore.entity.user.User;
import pl.kul.onlinestore.entity.user.UserModel;
import pl.kul.onlinestore.entity.user.VerificationToken;
import pl.kul.onlinestore.event.RegistrationCompleteEvent;
import pl.kul.onlinestore.service.user.PasswordResetTokenService;
import pl.kul.onlinestore.service.user.UserService;
import pl.kul.onlinestore.service.user.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v1")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService verificationTokenService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher publisher, VerificationTokenService verificationTokenService, PasswordResetTokenService passwordResetTokenService) {
        this.userService = userService;
        this.publisher = publisher;
        this.verificationTokenService = verificationTokenService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @PostMapping("users/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return ResponseEntity.ok(user);
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
        return ResponseEntity.ok("Verification Link was sent");
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

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel,
                                HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user, token);
        url = passwordResetTokenMail(user, applicationUrl(request), token);
        return url;
    }

    public String passwordResetTokenMail(User user,
                                         String applicationUrl,
                                         String token) {
        String url = applicationUrl +
                "/api/v1/passwordRecovery?token=" +
                token;

        // sendVerificationEmail();
        log.info("Link do odzyskiwania hasła: {}"
                , url);
        return url;
    }

    @PostMapping("/passwordRecovery")
    public ResponseEntity<?> passwordRecoveryByToken(@RequestParam("token") String token,
                                                     @RequestBody PasswordModel passwordModel) {
        String savedPassword = passwordResetTokenService.passwordRecoveryByToken(token, passwordModel);
        if(savedPassword.equals("done")){
            log.info("Password was changed");
            return ResponseEntity.ok("Hasło zostało zapisane");
        } else {
             return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordModel passwordModel){
        userService.changeExistingPasswordIfMatches(passwordModel);
        return ResponseEntity.ok("Hasło zostało zapisane");
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

