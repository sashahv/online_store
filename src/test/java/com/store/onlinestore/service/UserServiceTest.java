//package com.store.onlinestore.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.store.onlinestore.entity.User;
//import com.store.onlinestore.exception.UserAlreadyExistsException;
//import com.store.onlinestore.exception.UserNotFoundException;
//import com.store.onlinestore.repository.UserRepository;
//import com.store.onlinestore.user.Gender;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ContextConfiguration(locations = {"/application-context.xml"})
//@ExtendWith(SpringExtension.class)
//class UserServiceTest {
//
////    @Test
////    void itShouldGetAllUsers() {
////        UserRepository userRepository = mock(UserRepository.class);
////        when(userRepository.findAll()).thenReturn(new ArrayList<>());
////        assertThrows(UserNotFoundException.class,
////                () -> (new UserService(userRepository)).getAllUsers());
////        verify(userRepository).findAll();
////    }
//
////    @Test
////    void itShouldGetUserById() throws ParseException {
////        String email = "s.hvozditskyi@gmail.com";
////        User user = new User(
////                email,
////                "123",
////                "Oleksandr",
////                "Hvozditskyi",
////                "2003-04-08",
////                "Ukraine",
////                Gender.MALE
////        );
////        user.setId(123L);
////        UserRepository userRepository = mock(UserRepository.class);
////        when(userRepository.findById(any())).thenReturn(Optional.of(user));
////        ResponseEntity<Optional<User>> actualUserById =
////                (new UserService(userRepository)).getUserById(123L);
////        assertTrue(actualUserById.getBody().isPresent());
////        assertEquals(1, actualUserById.getHeaders().size());
////        assertEquals(HttpStatus.OK, actualUserById.getStatusCode());
////        verify(userRepository).findById(any());
////    }
//
//    @Test
//    void itShouldCheckDoesUserCreate() throws ParseException {
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
//        user.setId(123L);
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.selectExistsEmail((String) any())).thenReturn(true);
//        when(userRepository.save((User) any())).thenReturn(user);
//        UserService userService = new UserService(userRepository);
//
//        String email1 = "s.hvozditskyi@gmail.com";
//        User user1 = new User(
//                email1,
//                "123",
//                "Oleksandr",
//                "Hvozditskyi",
//                "2003-04-08",
//                "Ukraine",
//                Gender.MALE
//        );
//        user.setId(123L);
//
//        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user1));
//        verify(userRepository).selectExistsEmail((String) any());
//    }
//
//    @Test
//    void itShouldDeleteUser() {
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
//        user.setId(123L);
//        Optional<User> ofResult = Optional.of(user);
//        UserRepository userRepository = mock(UserRepository.class);
//        doThrow(new UserNotFoundException("An error occurred")).when(userRepository).deleteById((Long) any());
//        when(userRepository.findById(any())).thenReturn(ofResult);
//        assertThrows(UserNotFoundException.class,
//                () -> (new UserService(userRepository)).deleteUser(123L));
//        verify(userRepository).findById(any());
//        verify(userRepository).deleteById(any());
//    }
//
//}
//
