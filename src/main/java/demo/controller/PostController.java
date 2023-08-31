package demo.controller;


import demo.AppConstants;
import demo.entities.Post;
import demo.exceptions.UserNotFoundException;
import demo.requests.CreatePostRequest;
import demo.response.GetPostResponse;
import demo.services.PostService;
import demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionStage;

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
    public CompletionStage<Post> createPost(@RequestBody CreatePostRequest createPostRequest) throws Exception{
       try {
           return postService.createPost(createPostRequest);
       }catch (UserNotFoundException e){
           throw new UserNotFoundException("User is not present");
       }catch(Exception e){
           throw new RuntimeException("Unknown exception");
       }
    }

//    @GetMapping("/getByUserId")
//    public CompletionStage<List<Post>> getPostsByUserId(@RequestParam(value = "userId", required = true) Integer userId) {
//
//        try {
//            return  postService.getPostsByUserId(userId);
//        }catch (UserNotFoundException e) {
//            throw new UserNotFoundException("User is not present");
//        }catch(Exception e){
//            throw new RuntimeException("Unknown exception");
//        }
//    }

    @GetMapping("/getByUserId")
    public GetPostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "userId" ) Integer userId){
        try {
            return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir, userId);
        }catch (UserNotFoundException e) {
            throw new UserNotFoundException("User is not present");
        }catch(Exception e){
            throw new RuntimeException("Unknown exception");
        }
    }
}
