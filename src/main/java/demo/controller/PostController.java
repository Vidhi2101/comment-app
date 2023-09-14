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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/post")
@Slf4j
public class PostController {
    public PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;

    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest createPostRequest) throws Exception {
        try {
            PostResponse post = postService.createPost(createPostRequest);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        } catch (UserNotFoundException | BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }


    @GetMapping("/getById/{postId}")
    public ResponseEntity<?> getPostId(
            @PathVariable String postId,
            @RequestParam(value = "includeComment", required = false) boolean includeComment) {
        try {
            GetPostResponse post = postService.getPostById(postId, includeComment);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            throw new PostNotFoundException("Post with Id " + postId);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getAllPosts(
            @PathVariable String userId,
            @RequestParam(value = "includeComment", required = false) boolean includeComment,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        try {
            GetPaginatedPostResponse response = postService.getAllPosts(pageNo, pageSize, sortDir, userId, includeComment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (PostNotFoundException | UserNotFoundException | BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
