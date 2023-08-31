package demo.requests;


import demo.entities.Comment;
import demo.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    private String metaData;
    private Integer postId;
    private Integer parentCommentId;

    public Comment toComment(String metaData, Post post, Integer parentCommentId){
        Comment comment = new Comment();
        comment.setMetadata(metaData);
        comment.setParentId(parentCommentId);
        comment.setPost(post);
        return comment;
    }
}
