package com.example.track.service;

import com.example.track.domain.Track;
import com.example.track.exception.TrackAlreadyFoundException;
import com.example.track.exception.TrackNotFoundException;

import java.util.List;

public interface TrackService {
    Track saveTrack(Track track) throws TrackAlreadyFoundException;
    List<Track> getAllTrack() throws Exception;
    boolean deleteTrack(int trackId) throws TrackNotFoundException;
    List<Track> getAllTrackByTrackRating() throws TrackNotFoundException;
    List<Track> getAllTrackByArtistName(String artistName) throws TrackNotFoundException;

}
