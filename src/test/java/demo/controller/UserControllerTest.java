package demo.controller;

import demo.repositories.PostRepository;
import demo.services.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {


    @InjectMocks
    PostServiceImpl postService;

    PostRepository postRepository;

    @Test
    void createUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getUserById() {
    }
}