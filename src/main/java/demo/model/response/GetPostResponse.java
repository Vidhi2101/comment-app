package demo.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPostResponse {
    private String postId;
    private List<GetCommentResponse> comment;
    private String description;
    private String userName;
    private String userId;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int voteType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdAt;

}