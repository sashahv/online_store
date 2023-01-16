package pl.kul.onlinestore.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kul.onlinestore.entity.user.PasswordModel;
import pl.kul.onlinestore.entity.user.User;
import pl.kul.onlinestore.entity.user.UserModel;
import pl.kul.onlinestore.exception.user.InvalidPasswordException;
import pl.kul.onlinestore.exception.user.PasswordsAreNotMatchedException;
import pl.kul.onlinestore.exception.user.UserNotFoundException;
import pl.kul.onlinestore.repository.user.UserRepository;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User fetchUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(String.format("Użytkownik z indeksem [%d] nie został znaleziony", userId))
        );
    }

    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(String.format("Użytkownik: [%s] nie został znaleziony", email))
        );
    }

    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setGender(userModel.getGender().getName());
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        generatePasswordIfMatches(user,
                userModel.getPassword(),
                userModel.getMatchingPassword());
        userRepository.save(user);
        return user;
    }

    public void updateUser(Long id, UserModel userModel) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(String.format("Użytkownik z indeksem [%d] nie został znaleziony", id));
        }

        String email = userModel.getEmail();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(String.format("Użytkownik: [%s] nie został znaleziony", email))
        );

        userRepository
                .findById(id)
                .ifPresent(updatedUser -> {
                    updatedUser.setEmail(userModel.getEmail());
                    updatedUser.setFirstName(userModel.getFirstName());
                    updatedUser.setLastName(userModel.getLastName());
                    updatedUser.setGender(userModel.getGender().getName());
                    userRepository.save(updatedUser);
                });
    }

    public void generatePasswordIfMatches(User user,
                                          String password,
                                          String matchingPassword) {
        if(passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidPasswordException("Nowe hasło jest rowne staremu");
        }

        if (password.equals(matchingPassword)) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } else {
            log.info("Passwords are not matched");
            throw new PasswordsAreNotMatchedException("Hasło nie jest prawidłowe");
        }
    }

    public void changeExistingPasswordIfMatches(PasswordModel passwordModel) {
        String oldPassword = passwordModel.getOldPassword();
        String newPassword = passwordModel.getNewPassword();
        String matchingPassword = passwordModel.getMatchingPassword();

        User user = fetchUserByEmail(passwordModel.getEmail());

        log.info("Checking if old password is equal to new password...");
        if (!checkIfValidOldPassword(oldPassword, user)) {
            throw new PasswordsAreNotMatchedException("Stare hasło nie prawidłowe");
        } else {
                log.info("Checking on matching...");
                if (newPassword.equals(matchingPassword)) {
                    changePassword(newPassword, user);
                } else {
                    log.info("Passwords are not matched");
                    throw new PasswordsAreNotMatchedException("Hasło nie zostało potwierdzone");
                }
        }
    }

    private void changePassword(String newPassword, User user) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private boolean checkIfValidOldPassword(String oldPassword, User user) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("Użytkownik z indeksem [%d] nie został znaleziony", id))
        );
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(String.format("Użytkownik: [%s] nie został znaleziony", email))
        );
    }
}
