package demo.controller;


import demo.AppConstants;
import demo.entities.Comment;
import demo.exceptions.PostNotFoundException;
import demo.exceptions.UserNotFoundException;
import demo.requests.CreateCommentRequest;
import demo.response.GetCommentResponse;
import demo.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comment")
@Slf4j
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController( CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest createCommentRequest) throws Exception{
        try{
            Comment comment = commentService.createComment(createCommentRequest);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }catch (PostNotFoundException e){
            throw new PostNotFoundException("Post not found");
        }catch (Exception e){
            throw new RuntimeException("Unknown exception");
        }

    }

    //    @GetMapping("/getCommentsForPost")
//    public CompletionStage<List<Comment>> getComments(@RequestParam(value = "commentId", required = false) Integer commentId,
//                                                      @RequestParam(value = "postId") Integer postId) {
//
//        return  commentService.getCommentByPostIdAndParentId(commentId, postId);
////        return new ResponseEntity<>(commentByPostIdAndParentId, HttpStatus.ACCEPTED);
//
//    }
    @GetMapping("/getByParentIdAndPostId")
    public GetCommentResponse getComments(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "commentId", required = false) Integer commentId,
            @RequestParam(value = "postId") Integer postId){
        try {
            return commentService.getCommentByPostIdAndParentId(pageNo, pageSize, sortBy, sortDir, commentId, postId);
        }catch (UserNotFoundException e) {
            throw new UserNotFoundException("User is not present");
        }catch(Exception e){
            throw new RuntimeException("Unknown exception");
        }
    }

}
