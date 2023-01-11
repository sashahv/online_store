package pl.kul.onlinestore.controller;

import pl.kul.onlinestore.entity.user.UserModel;
import pl.kul.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/update/")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserModel userModel) {
            userService.updateUser(id, userModel);
            return ResponseEntity.ok("Zmiany do użytkownika zostały zapisane");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
            userService.deleteUser(id);
            return ResponseEntity.ok("Użytkownik został usunięty");
    }
}
