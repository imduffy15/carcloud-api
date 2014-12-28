package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;

import ie.ianduffy.carcloud.service.TrackService;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import ie.ianduffy.carcloud.web.munic.dto.TrackDTO;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.inject.Inject;

/**
 * REST controller for allowing munic devices to submit information.
 */
@RestController
@RequestMapping("/app/munic")
public class MunicResource {
    
    @Inject
    private TrackService trackService;

    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody List<TrackDTO> trackDTOs) {
        trackService.create(trackDTOs);
    }
}
