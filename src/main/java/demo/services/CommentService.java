package demo.services;


import demo.model.request.CreateCommentRequest;
import demo.model.response.CommentResponse;
import demo.model.response.GetCommentResponse;
import demo.model.response.GetPaginatedCommentResponse;

import java.util.List;

public interface CommentService {

     GetCommentResponse getComment(String commentId);

     CommentResponse createComment(CreateCommentRequest createCommentRequest);

     GetPaginatedCommentResponse getCommentByPostIdAndParentId(int pageNo, int pageSize, String sortDir, String parentCommentId, String postId);

     List<GetCommentResponse> getReplies(String commentId, String postId, int replyCount);
}
