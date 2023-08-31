package demo.services;


import demo.entities.User;
import demo.exceptions.DuplicateUserException;
import demo.exceptions.UserNotFoundException;
import demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public CompletionStage<User> createUser(User user){
        try {
            return CompletableFuture.completedFuture(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUserException("Duplicate entry for username: " + user.getUserName());
        }
    }

    @Override
    public CompletionStage<User> getUserById(Integer user){
        return CompletableFuture.completedFuture(userRepository.findById(user).orElseThrow(() -> new UserNotFoundException("No user found")));

    }

    @Override
    public CompletionStage<User> getUser(String user){
        return CompletableFuture.completedFuture(userRepository.findByUserName(user).orElseThrow(() -> new UserNotFoundException("No user found")));
    }


//    public void updateUser(Integer userId){
//        User user = userRepository.getById(userId);
//        user.setLoggedInAt(System.currentTimeMillis());
//        return userRepository.save(user);
//    }

}
