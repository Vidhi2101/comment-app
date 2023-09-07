package demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
@Builder
public class CommentResponse {
    private String id;
    private String postId;
    private String data;
    private String userName;
    private Long likeCount;
    private Long dislikeCount;
}
