package com.example.track.controller;


import com.example.track.domain.Artist;
import com.example.track.domain.Track;
import com.example.track.exception.TrackAlreadyFoundException;
import com.example.track.service.TrackService;
import com.example.track.service.TrackServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    TrackServiceImpl trackService;

    @InjectMocks
    TrackController trackController;

    Track track1=null;
    Artist artist1=null;

    @BeforeEach
    void setup(){
        track1=new Track(1,"Naina",7,artist1);
        artist1=new Artist(1,"Arijit");
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
    }

    @AfterEach
    void tearDown(){
        track1=null;
        artist1=null;
    }
    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }
    @Test
    public void getTrackToSave() throws Exception{
        when(trackService.saveTrack(any())).thenReturn(track1);
        mockMvc.perform(post("/trackDetail/api/track")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonToString(track1)))
               .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).saveTrack(any());
    }


    @Test
    public void givenTracktoSaveFailure() throws Exception {
        when(trackService.saveTrack(any())).thenThrow(TrackAlreadyFoundException.class);
        mockMvc.perform(post("/trackDetail/api/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(track1)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).saveTrack(any());
    }

    @Test
    public void givenTrackIdToDelete() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/trackDetail/api/track2/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).deleteTrack(anyInt());

    }
}
