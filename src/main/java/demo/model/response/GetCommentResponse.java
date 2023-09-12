package demo.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder
public class GetCommentResponse {
    private String commentId;
    private String postId;
    private String data;
    private String userName;
    private String userId;
    private int likeCount;
    private int dislikeCount;
    private int voteType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdAt;
}
