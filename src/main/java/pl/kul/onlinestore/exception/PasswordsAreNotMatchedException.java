package pl.kul.onlinestore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PasswordsAreNotMatchedException extends RuntimeException{
    public PasswordsAreNotMatchedException(String message) {
        super(message);
    }
}
