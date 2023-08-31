package demo.requests.User;


import demo.entities.User;

public record CreateUserRequest(String name, String mailId) {
    public User toUser(){
        User user = new User();
        user.setUserName(name);
        user.setMail(mailId);
        return user;
    }
}
