package demo.controller;

import demo.exceptions.BadRequestException;
import demo.exceptions.DuplicateUserException;
import demo.exceptions.UserNotFoundException;
import demo.model.request.CreateUserRequest;
import demo.model.response.UserResponse;
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


    //TODO: check why it is not throwing DuplicateUser exception?
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            UserResponse user = userService.createUser(createUserRequest);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (DuplicateUserException | BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUser(@PathVariable String userName) {
        try {
            UserResponse user = userService.getUser(userName);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (UserNotFoundException  | BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            UserResponse user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (UserNotFoundException | BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }


}
