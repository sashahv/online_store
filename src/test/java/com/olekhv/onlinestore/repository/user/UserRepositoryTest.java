package com.olekhv.onlinestore.repository.user;

import com.olekhv.onlinestore.entity.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private TestEntityManager entityManager;


//    private User user;
//    @BeforeAll
//    void setUp() {
//        user = User.builder()
//                .id(1L)
//                .firstName("Oleksandr")
//                .lastName("Hvozditskyi")
//                .gender("Male")
//                .email("s.hvozditskyi@gmail.com")
//                .enabled(true)
//                .password("123456")
//                .build();
//    }

    @Test
    void should_find_no_users_with_given_email(){
        Optional<User> user = userRepository.findByEmail("s.hvozditskyi@gmail.com");

        assertThat(user).isEmpty();
    }

    @Test
    void should_find_user_with_given_email() {
        Optional<User> user = userRepository.findByEmail("s.hvozditskyi@gmail.com");

        assertThat(user).isPresent();
    }
}