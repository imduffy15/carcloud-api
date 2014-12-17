package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing Device.
 */
@RestController
@RequestMapping("/app/rest/devices/{device_id}/tracks")
public class DeviceTracksResource {

    @Inject
    private DeviceService deviceService;
    @Inject
    private TrackDTOAssembler trackDTOAssembler;

    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@PathVariable("device_id") Long deviceId, @Valid @RequestBody TrackDTO trackDTO) {
        deviceService.addTrack(deviceId, trackDTO);
    }

    @RequestMapping(value = "/{index}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable("device_id") Long deviceId, @PathVariable("index") int index) {
        deviceService.removeTrack(deviceId, index);
    }

    @RequestMapping(value = "/{index}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> get(@PathVariable("device_id") Long deviceId, @PathVariable("index") int index) {
        return new ResponseEntity<>(deviceService.getTrack(deviceId, index), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAll(@PathVariable("device_id") Long deviceId) {
        List<TrackDTO> tracks = new ArrayList<>();
        for (Track track : deviceService.getTracks(deviceId)) {
            tracks.add(trackDTOAssembler.toResource(track));
        }
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }
}
