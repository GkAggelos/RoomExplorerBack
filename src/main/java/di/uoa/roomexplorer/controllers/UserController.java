package di.uoa.roomexplorer.controllers;

import di.uoa.roomexplorer.auth.AuthenticationResponse;
import di.uoa.roomexplorer.model.User;
import di.uoa.roomexplorer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update/{role}/{username}")
    public ResponseEntity<AuthenticationResponse> updateUser(@RequestBody User user, @PathVariable("role") String role, @PathVariable("username") String username) {
        AuthenticationResponse token = userService.updateUser(username, role, user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{role}/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("role") String role, @PathVariable("username") String username) {
        userService.deleteUser(username, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
