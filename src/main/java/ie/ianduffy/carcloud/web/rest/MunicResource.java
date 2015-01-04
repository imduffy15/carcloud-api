package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.service.TrackService;
import ie.ianduffy.carcloud.web.munic.dto.EventDTO;
import ie.ianduffy.carcloud.web.munic.dto.TrackDTO;

import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * REST controller for allowing munic devices to submit information.
 */
@Api(
    value = "munic",
    description = "Munic API",
    hidden = true
)
@RestController
@RequestMapping("/app/munic")
public class MunicResource {

    private Mapper mapper;

    private DeviceService deviceService;

    @Inject
    public MunicResource(Mapper mapper, DeviceService deviceService) {
        this.mapper = mapper;
        this.deviceService = deviceService;
    }

    @Timed
    @ApiOperation(
        value = "Create events",
        notes = "Creates events for specific devices based of the given payload"
    )
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
        @ApiParam(value = "a munic.io event", required = true) @RequestBody List<EventDTO> eventDTOs
    ) {
        List<TrackDTO> processedEvents = new ArrayList<>();
        for (EventDTO eventDTO : eventDTOs) {
            if (eventDTO.getMeta().getEvent().equals("track")) {
                TrackDTO trackDTO = new TrackDTO();
                mapper.map(eventDTO.getPayload(), trackDTO);
                deviceService.addTrack(trackDTO);
                processedEvents.add(trackDTO);
            }
        }
        return new ResponseEntity<>(processedEvents, HttpStatus.OK);
    }
}
