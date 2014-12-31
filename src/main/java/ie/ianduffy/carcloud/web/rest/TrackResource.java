package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.service.TrackService;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import ie.ianduffy.carcloud.web.dto.TrackDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * REST controller for managing tracks.
 */
@Api(
    value = "track",
    description = "Track API"
)
@RestController
@RequestMapping("/app/rest/tracks")
public class TrackResource {

    @Inject
    private TrackDTOAssembler trackDTOAssembler;

    @Inject
    private TrackService trackService;

    @Timed
    @ApiOperation(
        value = "Delete track",
        notes = "Deletes the specified track"
    )
    @RequestMapping(
        value = "/{track_id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void delete(
        @ApiParam(value = "track to be deleted", required = true) @PathVariable("track_id") Long trackId
    ) {
        trackService.delete(trackId);
    }

    @Timed
    @ApiOperation(
        value = "Get track",
        notes = "Gets the specified track"
    )
    @RequestMapping(
        value = "/{device_id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> get(
        @ApiParam(value = "track to get", required = true) @PathVariable("track_id") Long trackId
    ) {
        Track track = trackService.findOneForCurrentUser(trackId);
        if (track == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trackDTOAssembler.toResource(track), HttpStatus.OK);
    }

    @Timed
    @ApiOperation(
        value = "Get all tracks",
        notes = "Gets all tracks",
        hidden = true
    )
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAll() {
        List<Track> tracks = trackService.findAllForCurrentUser();
        List<TrackDTO> trackDTOs = new LinkedList<>();
        for (Track track : tracks) {
            trackDTOs.add(trackDTOAssembler.toResource(track));
        }
        return new ResponseEntity<>(trackDTOs, HttpStatus.OK);
    }
}
