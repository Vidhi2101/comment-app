package demo.model.request;


import demo.entities.Comment;
import demo.entities.Post;
import demo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    private String metaData;
    private String postId;
    private String parentCommentId;
    private String userId;

    public Comment toComment(String metaData, Post post, UUID parentCommentId, User user){
        Comment comment = new Comment();
        comment.setMetadata(metaData);
        comment.setParentId(parentCommentId);
        comment.setPost(post);
        comment.setUser(user);
        return comment;
    }
}
