package demo.controller;


import demo.AppConstants;
import demo.exceptions.BadRequestException;
import demo.exceptions.PostNotFoundException;
import demo.exceptions.UserNotFoundException;
import demo.model.request.CreateCommentRequest;
import demo.model.response.CommentResponse;
import demo.model.response.GetCommentResponse;
import demo.model.response.GetPaginatedCommentResponse;
import demo.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequest createCommentRequest) throws Exception{
        try{
            CommentResponse comment = commentService.createComment(createCommentRequest);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }catch (PostNotFoundException | UserNotFoundException  | BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }

    }

    @GetMapping("/viewComments/{postId}")
    public ResponseEntity<?> getComments(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "parentId", required = false) String commentId,
            @PathVariable(value = "postId") String postId){
        try {
            GetPaginatedCommentResponse response = commentService.getCommentByPostIdAndParentId(pageNo, pageSize, sortDir, commentId, postId);
            return new  ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (PostNotFoundException | BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/viewReplies/postId/{postId}/parentId/{commentId}")
    public ResponseEntity<?> getReplies(
            @PathVariable(value = "postId", required = false) String postId,
            @RequestParam(value = "replyCount", defaultValue = "10", required = false) Integer replyCount,
            @PathVariable(value = "commentId") String commentId){
        try {
            List<GetCommentResponse> response = commentService.getReplies(commentId, postId, replyCount);
            return new  ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (PostNotFoundException | BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

}
