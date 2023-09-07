package demo.controller;


import demo.AppConstants;
import demo.entities.Post;
import demo.exceptions.UserNotFoundException;
import demo.requests.CreatePostRequest;
import demo.response.GetPostResponse;
import demo.response.PostResponse;
import demo.services.PostService;
import demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/v1/post")
@Slf4j
public class PostController {
    public PostService postService;
    public UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest createPostRequest) throws Exception{
       try {
           Post post  = postService.createPost(createPostRequest);
           return new ResponseEntity<>(post, HttpStatus.CREATED);
       }catch (UserNotFoundException e){
           throw new UserNotFoundException("User is not present");
       }catch(Exception e){
           throw new RuntimeException("Unknown exception");
       }
    }


    @GetMapping("/getPost/{postId}")
    public PostResponse getAllPosts(
            @PathVariable UUID postId) {
        try {
            return postService.getPostById(postId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User is not present");
        } catch (Exception e) {
            throw new RuntimeException("Unknown exception");
        }
    }

    //you can write query for multiple level of comments as well. for now, havw written it for one level of comment

    @GetMapping("/getByUserId/{userId}")
    public GetPostResponse getAllPosts(
            @PathVariable UUID userId,
            @RequestParam(value = "includeComment", required = false) boolean includeComment,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        try {
            return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir, userId,includeComment);
        }catch (UserNotFoundException e) {
            throw new UserNotFoundException("User is not present");
        }catch(Exception e){
            throw new RuntimeException("Unknown exception");
        }
    }
}
