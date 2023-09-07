package demo.services;


import demo.entities.User;

import java.util.UUID;
import java.util.concurrent.CompletionStage;


public interface UserService {


     User createUser(User user);

     User getUserById(UUID user);
     User getUser(String user);


//    }

}
