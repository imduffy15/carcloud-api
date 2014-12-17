package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.service.TrackService;
import ie.ianduffy.carcloud.web.assembler.DeviceDTOAssembler;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import ie.ianduffy.carcloud.web.dto.DeviceDTO;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;

import java.util.List;

/**
 * REST controller for managing tracks.
 */
@RestController
@RequestMapping("/app/rest/tracks/{track_id}")
public class TrackResource {

    @Inject
    private TrackService trackService;
    @Inject
    private TrackDTOAssembler trackDTOAssembler;

    @RequestMapping(value = "/{track_id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable("track_id") Long trackId) {
        trackService.delete(trackId);
    }

    @RequestMapping(value = "/{device_id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> get(@PathVariable("track_id") Long trackId) {
        Track track = trackService.findOne(trackId);
        if (track == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trackDTOAssembler.toResource(track), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAll() {
        List<Track> tracks = trackService.findAll();
        List<TrackDTO> trackDTOs = new LinkedList<>();
        for (Track track : tracks) {
            trackDTOs.add(trackDTOAssembler.toResource(track));
        }
        return new ResponseEntity<>(trackDTOs, HttpStatus.OK);
    }

}
