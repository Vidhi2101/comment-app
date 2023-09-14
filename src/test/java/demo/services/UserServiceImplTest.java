package demo.services;

import demo.entities.User;
import demo.exceptions.BadRequestException;
import demo.exceptions.UserNotFoundException;
import demo.model.request.CreateUserRequest;
import demo.model.response.UserResponse;
import demo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Slf4j
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Mock
     CreateUserRequest createUserRequest;

    User user;

    @BeforeEach
    public void setup(){
        user = new User();
        user.setUserName("abc123");
        user.setMail("mmgg@gmai.com11312");
        user.setPassword("pwd");
        user.setCreatedAt(new Date());
        user.setLoggedInAt(new Date());
    }

    @Test
    void createUser() {
        when(createUserRequest.getName()).thenReturn(user.getUserName());
        when(createUserRequest.toUser()).thenReturn(user);
        when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        UserResponse savedUser = userService.createUser(createUserRequest);
        log.info("savedUser : " + savedUser);
        assertThat(savedUser).isNotNull();
    }

    @Test
    void getUserById() {
        UUID uuid = UUID.randomUUID();
        user.setId(uuid);
        when(userRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(user));
        UserResponse userById = userService.getUserById(user.getId().toString());
        assertThat(userById.getUserId()).isEqualTo(uuid.toString());

    }


    @Test
    void getUser() {
        when(userRepository.findByUserName(any(String.class)))
                .thenReturn(Optional.of(user));
        UserResponse user1 = userService.getUser(user.getUserName());
        assertThat(user1.getUserName()).isEqualTo("abc123");
    }

    @Test
    void getUserNotFoundException() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser("123");
        });
        String expectedMessage = "No user found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void createUserExpectedException() {
        User user1 = new User();
        user1.setUserName(null);
        when(createUserRequest.getName()).thenReturn(user1.getUserName());
        when(createUserRequest.toUser()).thenReturn(user1);
        Exception exception = assertThrows(BadRequestException.class, () -> {
            userService.createUser(createUserRequest);
        });
        String expectedMessage = "Invalid input: username is required.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}