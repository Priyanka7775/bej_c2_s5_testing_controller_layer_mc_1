package com.example.track.controller;

import com.example.track.domain.Track;
import com.example.track.exception.TrackAlreadyFoundException;
import com.example.track.exception.TrackNotFoundException;
import com.example.track.service.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trackDetail/api")
public class TrackController {
    private TrackService trackService;
    public TrackController(TrackService trackService){
        this.trackService=trackService;
    }
    @PostMapping("/track")
    public ResponseEntity<?>insertTrack(@RequestBody Track track)throws TrackAlreadyFoundException {
        ResponseEntity responseEntity=null;
        try{
            responseEntity=new ResponseEntity<>(trackService.saveTrack(track),HttpStatus.CREATED);
        }catch (TrackAlreadyFoundException exception){
            throw new TrackAlreadyFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //Track track1=trackService.saveTrack(track);
        return responseEntity;
    }
    @GetMapping("/track1")
    public ResponseEntity<?> fetchAllTrack()throws Exception{
        return new ResponseEntity<>(trackService.getAllTrack(),HttpStatus.OK);
    }
    @DeleteMapping("/track2/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable("trackId") int trackId) throws TrackNotFoundException{
        ResponseEntity responseEntity=null;
        try{
            trackService.deleteTrack(trackId);
            responseEntity=new ResponseEntity("Succesfully Deleted",HttpStatus.OK);
        }catch (TrackNotFoundException trackNotFoundException){
            throw new TrackNotFoundException();
        }catch (Exception exception){
            responseEntity=new ResponseEntity(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/track3")
    public ResponseEntity<?> fetchByTrackRating()throws TrackNotFoundException{
        ResponseEntity responseEntity=null;
        try{
            responseEntity =new ResponseEntity(trackService.getAllTrackByTrackRating(),HttpStatus.FOUND);

        }catch (TrackNotFoundException trackNotFoundException){
            throw new TrackNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("/track4/{artistName}")
    public ResponseEntity<?> fetchByArtistName(@PathVariable String artistName)throws TrackNotFoundException{
        ResponseEntity responseEntity=null;
        try{
            responseEntity =new ResponseEntity(trackService.getAllTrackByArtistName(artistName),HttpStatus.FOUND);

        }catch (TrackNotFoundException trackNotFoundException){
            throw new TrackNotFoundException();
        }
        return responseEntity;
    }
}
