package demo.requests;


import demo.entities.Post;
import demo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreatePostRequest {


    private String meta;
    private Integer userId;

    public Post toPost(String meta, User user){
        Post post = new Post();
        post.setUser(user);
        post.setMetaData(meta);
        return post;
    }
}
