package demo.services;


import demo.AppConstants;
import demo.entities.User;
import demo.exceptions.BadRequestException;
import demo.exceptions.DuplicateUserException;
import demo.exceptions.UserNotFoundException;
import demo.model.response.UserResponse;
import demo.model.response.mapper.UserMapper;
import demo.repositories.UserRepository;
import demo.model.request.CreateUserRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.UUID;


@Service
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        try {
            if (request == null || request.getName() == null)  {
                throw new BadRequestException("Invalid input: username is required.");
            }
            User user = userRepository.saveAndFlush(request.toUser());
            return UserMapper.mapToResponse(user);

            //TODO: its not throwing duplicate exception, fix me
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUserException("Duplicate User with userName " + request.getName());
        }
    }

    @Override
    public UserResponse getUserById(String userId){
        User user = userRepository.findById(convertToUUID(userId)).orElseThrow(() -> new UserNotFoundException("No user found"));
        return UserMapper.mapToResponse(user);
    }

    @Override
    public UserResponse getUser(String user){
        User userResponse =  userRepository.findByUserName(user).orElseThrow(() -> new UserNotFoundException("No user found"));
        return UserMapper.mapToResponse(userResponse);
    }

    private UUID convertToUUID(String id){
        try {
            return AppConstants.convertToUUID(id);
        }catch (IllegalArgumentException ex){
            throw new BadRequestException("Parameter is incorrect");
        }
    }


}
