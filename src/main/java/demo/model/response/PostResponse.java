package demo.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import demo.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Data
@Builder
public class PostResponse {
    private String message;
        private String postId;
        private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime createdAt;
}
