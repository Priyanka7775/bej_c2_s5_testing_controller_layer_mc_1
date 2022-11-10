package com.example.track.repository;

import com.example.track.domain.Artist;
import com.example.track.domain.Track;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class TrackRepositoryTest {

    @Autowired
    private TrackRepository trackRepository;
    private Artist artist;
    private Track track;

    @BeforeEach
    public void setup(){
        artist=new Artist(1,"Arijit Singh");
        track=new Track(1,"Naina",7,artist);
    }

    @AfterEach
    public void tearDown(){
        artist=null;
        track=null;
        trackRepository.deleteAll();
    }

    @Test
    @DisplayName("Test case for saving Track")
    void getTrackToSave(){
        trackRepository.save(track);
        Track track1=trackRepository.findById(track.getTrackId()).get();
        assertEquals(track.getTrackId(),track1.getTrackId());
    }

    @Test
    @DisplayName("Test case for deleting track")
    public void getTrackToDelete(){
        trackRepository.insert(track);
        Track track1=trackRepository.findById(track.getTrackId()).get();
        trackRepository.delete(track1);
        assertEquals(Optional.empty(),trackRepository.findById(track.getTrackId()));
    }

    @Test
    @DisplayName("Test case for retriving all the track object")
    public void getTrackReturnAllTrackDetail(){
        trackRepository.insert(track);
        artist=new Artist(2,"Neha");
        track=new Track(2,"Kala Chashma",8,artist);
        trackRepository.insert(track);

        List<Track> list=trackRepository.findAll();
        assertEquals(2,list.size());
        assertEquals("Kala Chashma",list.get(1).getTrackName());
    }



    @Test
    @DisplayName("Test case for retrieving all the track by Artist Name")
    public void getTrackByArtistName(){
        trackRepository.insert(track);
        artist=new Artist(2,"Neha");
        track=new Track(2,"Kala Chashma",8,artist);
        trackRepository.insert(track);
        List<Track> tracks=trackRepository.findAllTrackByArtistName(track.getTrackArtist().getArtistName());
        assertEquals(1,tracks.size());
        assertEquals(track.getTrackArtist().getArtistName(),tracks.get(0).getTrackArtist().getArtistName());
    }

    @Test
    @DisplayName("Test case for retrieving all the track by Track Rating")
    public void getTrackByTrackRating(){
        trackRepository.insert(track);
        artist=new Artist(2,"Neha");
        track=new Track(2,"Kala Chashma",3,artist);
        trackRepository.insert(track);
        List<Track> tracks=trackRepository.findAllTrackByTrackRating();
        assertEquals(1,tracks.size());

    }





}
