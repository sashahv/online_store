package com.olekhv.onlinestore.controller;

import com.olekhv.onlinestore.entity.user.PasswordModel;
import com.olekhv.onlinestore.service.user.PasswordResetTokenService;
import com.olekhv.onlinestore.service.user.UserService;
import com.olekhv.onlinestore.entity.user.User;
import com.olekhv.onlinestore.entity.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/users")
public class UserController {
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public UserController(UserService userService,
                          PasswordResetTokenService passwordResetTokenService) {
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> fetchUserByEmail(@PathVariable("email") String email){
        User user = userService.fetchUserByEmail(email);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel,
                                HttpServletRequest request) {
        User user = userService.fetchUserByEmail(passwordModel.getEmail());
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user, token);
        return passwordResetTokenMail(user, applicationUrl(request), token);
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

    public String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl +
                "/api/v1/users/passwordRecovery?token=" +
                token;

        // sendVerificationEmail();
        log.info("Link do odzyskiwania hasła: {}"
                , url);
        return url;
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordModel passwordModel){
        userService.changeExistingPasswordIfMatches(passwordModel);
        return ResponseEntity.ok("Hasło zostało zapisane");
    }
    @PutMapping("/{id}/update/")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserModel userModel) {
            userService.updateUser(id, userModel);
            return ResponseEntity.ok("Zmiany do użytkownika zostały zapisane");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
            userService.deleteUser(id);
            return ResponseEntity.ok("Użytkownik został usunięty");
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
