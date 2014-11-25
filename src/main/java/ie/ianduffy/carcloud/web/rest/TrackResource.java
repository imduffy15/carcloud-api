package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.repository.DeviceRepository;
import ie.ianduffy.carcloud.repository.TrackRepository;
import ie.ianduffy.carcloud.web.rest.dto.TrackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for managing Track.
 */
@RestController
@RequestMapping("/app")
public class TrackResource {

    private final Logger log = LoggerFactory.getLogger(TrackResource.class);

    @Inject
    private TrackRepository trackRepository;

    @Inject
    private DeviceRepository deviceRepository;

    /**
     * POST  /rest/tracks -> Create a new track.
     */
    @RequestMapping(value = "/rest/tracks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Track create(@RequestBody TrackDTO track) {
        log.debug("REST request to save Track : {}", track);
        Device device = deviceRepository.findOne(track.getPayload().getDeviceId());

        Track newTrack = new Track(device, track.getPayload().getLocation(),
            track.getPayload().getReceivedAt(), track.getPayload().getRecordedAt());
        trackRepository.save(newTrack);
        return newTrack;
    }


    /**
     * GET  /rest/tracks -> get all the tracks.
     */
    @RequestMapping(value = "/rest/tracks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Track> getAll() {
        log.debug("REST request to get all Tracks");
        return trackRepository.findAll();
    }
//
//    /**
//     * GET  /rest/tracks/:id -> get the "id" track.
//     */
//    @RequestMapping(value = "/rest/tracks/{id}",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<Track> get(@PathVariable Long id, HttpServletResponse response) {
//        log.debug("REST request to get Track : {}", id);
//        Track track = trackRepository.findOne(id);
//        if (track == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(track, HttpStatus.OK);
//    }
//
//    /**
//     * DELETE  /rest/tracks/:id -> delete the "id" track.
//     */
//    @RequestMapping(value = "/rest/tracks/{id}",
//            method = RequestMethod.DELETE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public void delete(@PathVariable Long id) {
//        log.debug("REST request to delete Track : {}", id);
//        trackRepository.delete(id);
//    }
}
