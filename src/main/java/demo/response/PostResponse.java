package demo.response;

import demo.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String id;
    private List<CommentResponse> comment;
    private String description;
    private String userName;
    private Long likeCount;
    private Long dislikeCount;
    private Integer commentCount;

}
