package com.olekhv.onlinestore.service.user;

import com.olekhv.onlinestore.entity.user.PasswordResetToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.olekhv.onlinestore.entity.user.PasswordModel;
import com.olekhv.onlinestore.entity.user.User;
import com.olekhv.onlinestore.exception.user.UserNotFoundException;
import com.olekhv.onlinestore.repository.user.PasswordResetTokenRepository;

import java.util.Calendar;
import java.util.Optional;

@Service
@Slf4j
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserService userService;

    @Autowired
    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository, UserService userService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userService = userService;
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken =
                new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            return "invalid";
        }

        Calendar calendar = Calendar.getInstance();

        if (passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid";
    }

    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    public String passwordRecoveryByToken(String token, PasswordModel passwordModel) {

        String result = validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")) {
            return "invalidToken";
        } else {
            log.info("Searching for user...");
            Optional<User> userByPasswordResetToken = getUserByPasswordResetToken(token);

            if (userByPasswordResetToken.isEmpty()) {
                throw new UserNotFoundException(String.format("Użytkownik z tokenem: [%s] nie istnieje", token));
            } else {
                User user = userByPasswordResetToken.get();

                log.info("User {} {} was found by token", user.getFirstName(), user.getLastName());
                userService.generatePasswordIfMatches(user,
                        passwordModel.getNewPassword(),
                        passwordModel.getMatchingPassword());
                log.info("Saving password...");
                return "done";
            }
        }
    }
}
