package demo.model.request;


import demo.entities.User;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String mailId;

    public User toUser(){
        User user = new User();
        user.setUserName(name);
        user.setMail(mailId);
        return user;
    }
}
