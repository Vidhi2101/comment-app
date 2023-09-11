package demo.controller;


import demo.AppConstants;
import demo.exceptions.BadRequestException;
import demo.exceptions.PostNotFoundException;
import demo.exceptions.UserNotFoundException;
import demo.model.request.CreatePostRequest;
import demo.model.response.GetPaginatedPostResponse;
import demo.model.response.GetPostResponse;
import demo.model.response.PostResponse;
import demo.services.PostService;
import demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest createPostRequest) throws Exception{
       try {
           PostResponse post  = postService.createPost(createPostRequest);
           return new ResponseEntity<>(post, HttpStatus.CREATED);
       }catch (UserNotFoundException | BadRequestException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
       }
    }



    @GetMapping("/getPost/{postId}")
    public ResponseEntity<?> getAllPosts(
            @PathVariable String postId) {
        try {
            GetPostResponse post = postService.getPostById(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (PostNotFoundException |BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    //you can write query for multiple level of comments as well. for now, have written it for one level of comment

    //TODOin respone, check why last = true where it should not
    //When soneone sends a wrong UUID formaty, it throws weird error. if time permit, change to long is better to demo purpose
    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getAllPosts(
            @PathVariable String userId,
            @RequestParam(value = "includeComment", required = false) boolean includeComment,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        try {
            GetPaginatedPostResponse response = postService.getAllPosts(pageNo, pageSize,  sortDir, userId,includeComment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (PostNotFoundException |UserNotFoundException |BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
