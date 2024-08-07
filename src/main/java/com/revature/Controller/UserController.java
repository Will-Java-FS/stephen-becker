package com.revature.Controller;

import com.revature.Model.User;
import com.revature.Service.SongService;
import com.revature.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    UserService userService;
    SongService songService;

    @Autowired
    public UserController(UserService userService, SongService songService){
        this.userService = userService;
        this.songService = songService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registration(@RequestBody User user){
        if(userService.login(user.getUserName()) != null){
            return ResponseEntity.status(400).build();
        }
        else{
            return ResponseEntity.status(200).body(userService.register(user));
        }
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        User u = userService.login((user.getUserName()));

        if(u.getUserName() == null || !u.getPassword().equals(user.getPassword())){
            return ResponseEntity.status(401).build();
        }
        else{
            return ResponseEntity.status(200).body(u);
        }
    }

    @GetMapping("/users/{userID}")
    public ResponseEntity<List<User>> getUsersAndSongs(@PathVariable Long userID){

        List<User> users = userService.getAllUsers(userID);
        return ResponseEntity.status(200).body(users);
    }
}
