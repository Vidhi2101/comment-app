package demo.services;


import demo.entities.Comment;
import demo.requests.CreateCommentRequest;
import demo.response.GetCommentResponse;

import java.util.UUID;

public interface CommentService {

     Comment createComment(CreateCommentRequest createCommentRequest);

     GetCommentResponse getCommentByPostIdAndParentId(int pageNo, int pageSize, String sortBy, String sortDir, UUID parentCommentId, UUID postId);

}
