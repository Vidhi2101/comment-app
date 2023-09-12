package demo.model.response.mapper;

import demo.entities.User;
import demo.model.response.UserResponse;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public static UserResponse mapToResponse(User user){
        return  new UserResponse(String.valueOf(user.getId()), user.getMail(), user.getUserName(), user.getCreatedAt());

    }
}
