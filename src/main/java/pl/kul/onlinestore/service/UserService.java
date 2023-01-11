package pl.kul.onlinestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kul.onlinestore.entity.VerificationToken;
import pl.kul.onlinestore.entity.user.User;
import pl.kul.onlinestore.entity.user.UserModel;
import pl.kul.onlinestore.exception.PasswordsAreNotMatchedException;
import pl.kul.onlinestore.exception.UserNotFoundException;
import pl.kul.onlinestore.repository.UserRepository;
import pl.kul.onlinestore.repository.VerificationTokenRepository;

import java.util.Calendar;
import java.util.Objects;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User fetchUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(String.format("Użytkownik z indeksem [%d] nie został znaleziony", userId))
        );
    }

    public User fetchUserByEmail(String email){
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
        changePasswordIfMatches(user, userModel);
        userRepository.save(user);
        return user;
    }

    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    public String saveVerificationToken(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "invalid";
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if (verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0){
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    public void updateUser(Long id, UserModel userModel) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(String.format("Użytkownik z indeksem [%d] nie został znaleziony", id));
        }
        userRepository
                .findById(id)
                .ifPresent(updatedUser -> {
                    updatedUser.setEmail(userModel.getEmail());
                    updatedUser.setFirstName(userModel.getFirstName());
                    updatedUser.setLastName(userModel.getLastName());
                    updatedUser.setGender(userModel.getGender().getName());
                    changePasswordIfMatches(updatedUser, userModel);
                    userRepository.save(updatedUser);
                });
    }

    public void changePasswordIfMatches(User user, UserModel userModel){
        if(isPasswordsMatches(userModel)) {
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        } else {
            log.info("Passwords are not matched");
            throw new PasswordsAreNotMatchedException("Hasło nie zostało potwierdzone");
        }
    }

    public boolean isPasswordsMatches(UserModel userModel){
        return Objects.equals(userModel.getPassword(), userModel.getMatchingPassword());
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("Użytkownik z indeksem [%d] nie został znaleziony", id))
        );
        userRepository.deleteById(id);
    }
}
