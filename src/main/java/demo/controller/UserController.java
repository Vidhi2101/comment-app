package demo.controller;

import demo.entities.User;
import demo.exceptions.DuplicateUserException;
import demo.requests.User.CreateUserRequest;
import demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {
    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            User user = userService.createUser(createUserRequest.toUser());
            ;
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (DuplicateUserException e) {
            throw new DuplicateUserException("User is already present");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUser(@PathVariable String userName) {
        User user = userService.getUser(userName);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


}
