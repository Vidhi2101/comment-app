package demo.model.response.mapper;

import demo.entities.Comment;
import demo.entities.Post;
import demo.entities.User;
import demo.entities.VoteType;
import demo.model.response.GetPostResponse;
import demo.model.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public static UserResponse mapToResponse(User user){
        return  new UserResponse(String.valueOf(user.getId()), user.getMail(), user.getUserName(), user.getCreatedAt());

    }
}
