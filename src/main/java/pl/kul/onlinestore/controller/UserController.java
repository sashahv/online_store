package pl.kul.onlinestore.controller;

import org.springframework.http.MediaType;
import pl.kul.onlinestore.entity.user.User;
import pl.kul.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
            userService.updateUser(id, user);
            return ResponseEntity.ok("Zmiany do użytkownika zostały zapisane");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
            userService.deleteUser(id);
            return ResponseEntity.ok("Użytkownik został usunięty");
    }
}
