package com.store.onlinestore.service;

import com.store.onlinestore.entity.User;
import com.store.onlinestore.exception.BadRequestException;
import com.store.onlinestore.exception.UserAlreadyExistsException;
import com.store.onlinestore.exception.UserNotFoundException;
import com.store.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id:" + id + " not found");
        }
        return user;
    }

    public void createUser(User user) throws UserAlreadyExistsException {
        if(!(userRepository.findByEmail(user.getEmail())==null)){
            throw new UserAlreadyExistsException("Użytkownik z takim e-mailem już istnieje");
        }
        userRepository.save(user);
    }

    public void updateUser(Long id, User user) throws UserNotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(String.format("Użytkownik z indeksem [%d] nie został znaleziony", id));
        }
        userRepository
                .findById(id)
                .ifPresent(updatedUser -> {
                    updatedUser.setFirstName(user.getFirstName());
                    updatedUser.setLastName(user.getLastName());
                    updatedUser.setBirthday(user.getBirthday());
                    updatedUser.setCountry(user.getCountry());
                    updatedUser.setGender(user.getGender());
                    userRepository.save(updatedUser);
                });
    }

    public void deleteUser(@PathVariable("id") Long id) throws UserNotFoundException  {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(String.format("Użytkownik z indeksem [%d] nie został znaleziony", id));
        }
        userRepository.deleteById(id);
    }

    public void changePassword(Long id, User user){
        userRepository
                .findById(id)
                .ifPresent(updatedUser -> {
                    updatedUser.setPassword(user.getPassword());
                    userRepository.save(updatedUser);
                });
    }

}
