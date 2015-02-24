package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import ie.ianduffy.carcloud.web.munic.dto.EventDTO;
import lombok.extern.java.Log;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for allowing munic devices to submit information.
 */
@Log
@Api(
    value = "munic",
    description = "Munic API",
    hidden = true
)
@RestController
@RequestMapping("/app/munic")
public class MunicResource {

    private DeviceService deviceService;

    private Mapper mapper;

    private TrackDTOAssembler trackDTOAssembler;

    @Inject
    public MunicResource(Mapper mapper, DeviceService deviceService, TrackDTOAssembler trackDTOAssembler) {
        this.mapper = mapper;
        this.deviceService = deviceService;
        this.trackDTOAssembler = trackDTOAssembler;
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
                try {
                    ie.ianduffy.carcloud.web.munic.dto.TrackDTO trackDTO = new ie.ianduffy.carcloud.web.munic.dto.TrackDTO();
                    mapper.map(eventDTO.getPayload(), trackDTO);
                    Track track = deviceService.addTrack(trackDTO);
                    processedEvents.add(trackDTOAssembler.toResource(track));
                } catch(IllegalArgumentException e) {
                    log.info("Ignoring track due to incomplete information.");
                }
            }
        }
        return new ResponseEntity<>(processedEvents, HttpStatus.OK);
    }
}
