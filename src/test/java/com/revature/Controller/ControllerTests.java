package com.revature.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Model.Song;
import com.revature.Model.User;
import com.revature.Service.SongService;
import com.revature.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = com.revature.Application.class)
public class ControllerTests {
    @MockBean
    SongService songService;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllSongs() throws Exception {
        Song song1 = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");
        Song song2 = new Song("Shake It Off", "The Weeknd", "Pop", "2020");
        List<Song> songList = songService.getAllSongs();
        songList.add(song1);
        songList.add(song2);

        Mockito.when(songService.getAllSongs()).thenReturn(songList);

        ResultActions ra = mvc.perform(get("/songs"));
        ra.andExpect(status().isOk());
    }

    @Test
    void saveSongSuccess() throws Exception {
        Song song = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");
        User user = new User("user1", "password1", "Bob", "Jones", true);

        Mockito.when(songService.saveSong(user.getUserID(), song)).thenReturn(song);

        String json = objectMapper.writeValueAsString(song);

        ResultActions ra = mvc.perform(post("/users/1/songs").contentType(MediaType.APPLICATION_JSON).content(json));
        ra.andExpect(status().isOk());
    }

    @Test
    void saveSongFailure() throws Exception {
        Song song = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");
        User user = new User("user1", "password1", "Bob", "Jones", true);

        Mockito.when(songService.saveSong(user.getUserID(), song)).thenReturn(song);

        ResultActions ra = mvc.perform(post("/users/2/songs"));
        ra.andExpect(status().isBadRequest());
    }

    @Test
    void addSong() throws Exception {
        Song song = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");

        Mockito.when(songService.addSong(song)).thenReturn(song);

        String json = objectMapper.writeValueAsString(song);

        ResultActions ra = mvc.perform(post("/songs").contentType(MediaType.APPLICATION_JSON).content(json));
        ra.andExpect(status().isOk());
    }

    @Test
    void getSongsBySongID() throws Exception {
        Song song = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");

        Mockito.when(songService.getSongsByID(song.getSongID())).thenReturn(song);

        ResultActions ra = mvc.perform(get("/songs/1"));
        ra.andExpect(status().isOk());
    }

    @Test
    void updateSongSuccess() throws Exception{
        Song song = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");

        Mockito.when(songService.updateSong(song.getSongID(), song)).thenReturn(song);

        String json = objectMapper.writeValueAsString(song);

        ResultActions ra = mvc.perform(patch("/songs/1").contentType(MediaType.APPLICATION_JSON).content(json));
        ra.andExpect(status().isOk());
    }

    @Test
    void updateSongFailure() throws Exception{
        Song song = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");

        Mockito.when(songService.updateSong(song.getSongID(), song)).thenReturn(song);

        ResultActions ra = mvc.perform(patch("/songs/2"));
        ra.andExpect(status().isBadRequest());
    }

    @Test
    void deleteSongSuccess() throws Exception {
        Song song = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");

        Mockito.when(songService.deleteSong(song.getSongID())).thenReturn(1);

        ResultActions ra = mvc.perform(delete("/songs/1"));
        ra.andExpect(status().isOk());
    }

    @Test
    void deleteSongFailure() throws Exception {
        Song song = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");

        Mockito.when(songService.deleteSong(song.getSongID())).thenReturn(0);

        ResultActions ra = mvc.perform(delete("/songs/1"));
        ra.andExpect(status().isOk());
    }

    @Test
    void getSongsByUser() throws Exception {
        User user = new User("user1", "password1", "Bob", "Jones", true);

        Mockito.when(songService.getAllSongsByUserID(user.getUserID())).thenReturn(user.getUserSongs());

        ResultActions ra = mvc.perform(get("/users/1/songs"));
        ra.andExpect(status().isOk());
    }

    @Test
    void registrationSuccess() throws Exception {
        User user = new User("user1", "password1", "Bob", "Jones", true);

        Mockito.when(userService.register(user)).thenReturn(user);

        String json = objectMapper.writeValueAsString(user);

        ResultActions ra = mvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(json));
        ra.andExpect(status().isOk());
    }

    @Test
    void registrationFailure() throws Exception {
        User user = new User(null, "password1", "Bob", "Jones", true);

        Mockito.when(userService.register(user)).thenReturn(user);

        ResultActions ra = mvc.perform(post("/register"));
        ra.andExpect(status().isBadRequest());
    }

    @Test
    void loginSuccess() throws Exception {
        User user = new User("Bob", "password1", "Bob", "Jones", true);

        Mockito.when(userService.login(user.getUserName())).thenReturn(user);

        String json = objectMapper.writeValueAsString(user);

        ResultActions ra = mvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content(json));
        ra.andExpect(status().isOk());
    }

    @Test
    void loginFailure() throws Exception {
        User user = new User(null, "password1", "Bob", "Jones", true);

        Mockito.when(userService.login(user.getUserName())).thenReturn(user);

        String json = objectMapper.writeValueAsString(user);

        ResultActions ra = mvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content(json));
        ra.andExpect(status().isUnauthorized());
    }

    @Test
    void getUsersAndSongs() throws Exception {
        User user = new User("Bob", "password1", "Bob", "Jones", true);
        List<User> userList = userService.getAllUsers(user.getUserID());
        userList.add(user);

        Mockito.when(userService.getAllUsers(user.getUserID())).thenReturn(userList);

        ResultActions ra = mvc.perform(get("/users/1"));
        ra.andExpect(status().isOk());
    }
}
