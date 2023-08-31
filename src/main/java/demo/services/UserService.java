package demo.services;


import demo.entities.User;

import java.util.concurrent.CompletionStage;


public interface UserService {


     CompletionStage<User> createUser(User user);

     CompletionStage<User> getUserById(Integer user);
     CompletionStage<User> getUser(String user);

//    public void updateUser(Integer userId){
//        User user = userRepository.getById(userId);
//        user.setLoggedInAt(System.currentTimeMillis());
//        return userRepository.save(user);
//    }

}
