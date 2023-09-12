package demo.services;


import demo.model.request.CreateUserRequest;
import demo.model.response.UserResponse;


public interface UserService {


     UserResponse createUser(CreateUserRequest request);

     UserResponse getUserById(String user);
     UserResponse getUser(String user);




}
