package com.revature.Service;

import com.revature.Model.Song;
import com.revature.Model.User;
import com.revature.Respository.SongRepository;
import com.revature.Respository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.when;

@SpringBootTest(classes = com.revature.Application.class)
public class ServiceTests {
    @Autowired
    UserService userService;

    @Autowired
    SongService songService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    SongRepository songRepository;

    @Test
    void addSong(){
        Song s = new Song(0L, "Blinding Lights", "The Weeknd", "Pop", "2020", null);

        Mockito.when(songRepository.save(s)).thenReturn(new Song(1L, "Blinding Lights", "The Weeknd", "Pop", "2020", null));

        s = songService.addSong(s);

        Assertions.assertEquals(1, s.getSongID());
        Assertions.assertEquals("Blinding Lights", s.getSongName());
    }

    @Test
    void deleteSong(){
        Song s = new Song(0L, "Blinding Lights", "The Weeknd", "Pop", "2020", null);

        Mockito.doNothing().when(songRepository).deleteById((s.getSongID()));

        int result = songService.deleteSong(s.getSongID());
        boolean r = false;

        if(result == 0){
            r = true;
        }

        Assertions.assertTrue(r);
    }

    @Test
    void getSongByID(){
        Song s = new Song(0L, "Blinding Lights", "The Weeknd", "Pop", "2020", null);
        Optional<Song> songOptional = Optional.of(s);

        Mockito.when(songRepository.findById(s.getSongID())).thenReturn(songOptional);

        Song actual = songService.getSongsByID(s.getSongID());
        Assertions.assertEquals(s.getSongID(), actual.getSongID());
    }

    @Test
    public void getSongs(){
        List<Song> songs = songRepository.findAll();
        Song u = new Song(0L, "Blinding Lights", "The Weeknd", "Pop", "2020", null);
        songs.add(u);

        Assertions.assertFalse(songs.isEmpty());
    }

    @Test
    void register(){
        User u = new User("user1", "password1", "John", "Jones", false);

        when(userRepository.save(u)).thenReturn(new User(1L, "user1", "password1", "John", "Jones", false, null));

        u = userService.register(u);

        Assertions.assertEquals(1, u.getUserID());
        Assertions.assertEquals("user1",u.getUserName());
    }

    @Test
    void login() {
        User u = new User("user1", "password1", "John", "Jones", false);

        Mockito.when(userRepository.findByUserName(u.getUserName())).thenReturn(u);

        User actual = userService.login(u.getUserName());
        Assertions.assertEquals(u.getUserName(), actual.getUserName());
        Assertions.assertEquals(u.getPassword(), actual.getPassword());
    }

    @Test
    public void getUsers(){
        List<User> users = userRepository.findAll();
        User u = new User("user1", "password1", "John", "Jones", false);
        users.add(u);

        Assertions.assertFalse(users.isEmpty());
    }
}
