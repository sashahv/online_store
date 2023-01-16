package pl.kul.onlinestore.controller;

import pl.kul.onlinestore.entity.user.User;
import pl.kul.onlinestore.entity.user.UserModel;
import pl.kul.onlinestore.service.user.UserService;
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

    @GetMapping("/{email}")
    public ResponseEntity<?> fetchUserByEmail(@PathVariable("email") String email){
        User user = userService.fetchUserByEmail(email);
        return ResponseEntity.ok(user);
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
