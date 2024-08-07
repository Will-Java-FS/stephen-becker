package com.revature.Repository;

import com.revature.Model.Song;
import com.revature.Respository.SongRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest(classes = com.revature.Application.class)
@Transactional
public class RepositoryTests {
    @Autowired
    public SongRepository songRepository;

    @Test
    void getAllSongs() {
        List<Song> songList = songRepository.findAll();
        Song song1 = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");
        Song song2 = new Song("Shake It Off", "The Weeknd", "Pop", "2020");
        songList.add(song1);
        songList.add(song2);
        System.out.println(songList);
        Assertions.assertFalse(songList.isEmpty());
    }

    @Test
    @Rollback
    void saveSong() {
        Song song = new Song("Blinding Lights", "The Weeknd", "Pop", "2020");
        song = songRepository.save(song);
        System.out.println(song);
        Assertions.assertEquals(1, song.getSongID());
    }
}
