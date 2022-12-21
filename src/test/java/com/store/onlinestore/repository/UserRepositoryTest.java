//package com.store.onlinestore.repository;
//
//import com.store.onlinestore.entity.User;
//import com.store.onlinestore.user.Gender;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;
//
//@SpringBootTest
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository underTest;
//
//    @Test
//    void itShouldCheckIfUserExistsByEmail() throws ParseException {
//        String email = "s.hvozditskyi@gmail.com";
//        User user = new User(
//                email,
//                "123",
//                "Oleksandr",
//                "Hvozditskyi",
//                "2003-04-08",
//                "Ukraine",
//                Gender.MALE
//        );
//        underTest.save(user);
//
//        boolean exists = underTest.selectExistsEmail(email);
//
//        assertThat(exists).isTrue();
//    }
//
//    @Test
//    void willThrowWhenEmailIsTaken() throws ParseException {
//        String email = "s.hvozditskyi@gmail.com";
//        User user = new User(
//                email,
//                "123",
//                "Oleksandr",
//                "Hvozditskyi",
//                "2003-04-08",
//                "Ukraine",
//                Gender.MALE
//        );
//
//        assertThatThrownBy(() -> underTest.save(user));
//    }
//}