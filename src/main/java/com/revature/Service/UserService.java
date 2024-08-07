package com.revature.Service;

import com.revature.Respository.UserRepository;
import com.revature.Model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User register(User user){
        return userRepository.save(user);
    }

    public User login(String username){
        return userRepository.findByUserName(username);
    }

    public List<User> getAllUsers(Long userID){
        User u = userRepository.findById(userID).get();
        if(u.isAdmin()) {
            return userRepository.findAll();
        } else {
            return null;
        }
    }
}
