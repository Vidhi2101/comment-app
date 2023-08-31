package demo.controller;


import demo.entities.User;
import demo.exceptions.DuplicateUserException;
import demo.requests.User.CreateUserRequest;
import demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {
    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public CompletionStage<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            return userService.createUser(createUserRequest.toUser());
        } catch (DuplicateUserException e) {
            throw new DuplicateUserException("User is already present");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> logIn(@RequestBody UserLoginRequest userLoginRequest) {
//        User user = userService.getUser(userLoginRequest.toUser());
//        return new ResponseEntity<>(String.format("%s is logged in", user.getUserName()), HttpStatus.CREATED);
//    }


    }
