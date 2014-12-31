package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import ie.ianduffy.carcloud.web.dto.TrackDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * REST controller for managing Device.
 */
@Api(
    value = "device tracks",
    description = "Device Tracks API"
)
@RestController
@RequestMapping("/app/rest/devices/{device_id}/tracks")
public class DeviceTracksResource {

    @Inject
    private DeviceService deviceService;

    @Inject
    private TrackDTOAssembler trackDTOAssembler;

    @Timed
    @ApiOperation(
        value = "Get device tracks",
        notes = "Gets all the tracks of the specified device"
    )
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAll(
        @ApiParam(value = "device to get tracks for", required = true) @PathVariable("device_id") Long deviceId
    ) {
        List<TrackDTO> tracks = new ArrayList<>();
        for (Track track : deviceService.getTracks(deviceId)) {
            tracks.add(trackDTOAssembler.toResource(track));
        }
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }
}
