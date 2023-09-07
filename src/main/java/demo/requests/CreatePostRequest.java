package demo.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import demo.entities.Post;
import demo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class CreatePostRequest {


    private String meta;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID userId;

    public Post toPost(String meta, User user){
        Post post = new Post();
        post.setUser(user);
        post.setMetaData(meta);
        return post;
    }
}
