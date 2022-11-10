package com.example.track.repository;

import com.example.track.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TrackRepository extends MongoRepository<Track,Integer> {
    @Query("{'trackRating':{$gt:4}}")
    List<Track> findAllTrackByTrackRating();

    @Query("{'trackArtist.artistName':{$in:[?0]}}")
    List<Track> findAllTrackByArtistName(String artistName);
}
