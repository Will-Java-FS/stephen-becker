package com.revature.Service;

import com.revature.Model.Song;
import com.revature.Model.User;
import com.revature.Respository.SongRepository;
import com.revature.Respository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SongService {
    SongRepository songRepository;
    UserRepository userRepository;

    @Autowired
    public SongService(SongRepository songRepository, UserRepository userRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    public Song saveSong(Long userID, Song song) {
        User u = userRepository.findById(userID).get();
        u.getUserSongs().add(song);
        return songRepository.save(song);
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public Song getSongsByID(Long songID) {
        return songRepository.findById(songID).get();
    }

    public List<Song> getAllSongsByUserID(Long userID){
        User u = userRepository.findById(userID).get();
        return u.getUserSongs();
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public int deleteSong(Long songID) {
        Optional<Song> song = songRepository.findById(songID);
        if(song.isPresent()) {
            songRepository.deleteById(songID);
            return 1;
        } else {
            return 0;
        }
    }

    public Song updateSong(Long songID, Song updatedSong) {
        Optional<Song> optionalSong = songRepository.findById(songID);
        if(optionalSong.isPresent()) {
            Song song = optionalSong.get();
            if(updatedSong.getSongName() != null) {
                song.setSongName(updatedSong.getSongName());
            }
            if(updatedSong.getArtist() != null) {
                song.setArtist(updatedSong.getArtist());
            }
            if(updatedSong.getGenre() != null) {
                song.setGenre(updatedSong.getGenre());
            }
            if(updatedSong.getYear() != null) {
                song.setYear(updatedSong.getYear());
            }
            songRepository.save(song);
            return song;
        } else {
            return null;
        }
    }
}
