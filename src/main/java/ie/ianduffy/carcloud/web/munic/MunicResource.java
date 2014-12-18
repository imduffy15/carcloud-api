package ie.ianduffy.carcloud.web.munic;

import com.codahale.metrics.annotation.Timed;
import ie.ianduffy.carcloud.service.TrackService;
import ie.ianduffy.carcloud.web.munic.dto.TrackDTO;
import ie.ianduffy.carcloud.assembler.TrackDTOAssembler;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for allowing munic devices to submit information.
 */
@RestController
@RequestMapping("/app/munic")
public class MunicResource {

    @Inject
    private TrackDTOAssembler trackDTOAssembler;
    @Inject
    private TrackService trackService;

    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody List<TrackDTO> trackDTOs) {
        trackService.create(trackDTOs);
    }
}
