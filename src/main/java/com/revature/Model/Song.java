package com.revature.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songID;
    @Column
    private String songName;
    @Column
    private String artist;
    @Column
    private String genre;
    @Column
    private String year;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "user_fk")
    private User userID;

    public Song(String songName, String artist, String genre, String year) {
        this.songName = songName;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
    }
}
