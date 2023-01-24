package com.olekhv.onlinestore.service.user;

import com.olekhv.onlinestore.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void should_return_false_if_old_password_is_not_correct(){
        User user = new User();
        user.setPassword(
                passwordEncoder.encode("12345"));

        boolean ifValidOldPassword = userService.checkIfValidOldPassword(
                "123456", user
        );

        assertTrue(ifValidOldPassword);
    }

    @Test
    public void should_return_true_if_old_password_is_correct(){
        User user = new User();
        user.setPassword(
                passwordEncoder.encode("12345"));

        boolean ifValidOldPassword = userService.checkIfValidOldPassword(
                "12345 ", user
        );

        assertTrue(ifValidOldPassword);
    }
}