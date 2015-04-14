package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import ie.ianduffy.carcloud.web.propertyeditors.DateTimeEditor;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(
    value = "device tracks",
    description = "Device Tracks API"
)
@RestController
@RequestMapping("/app/rest/devices/{device_id}/tracks")
public class DeviceTracksResource {

    private DeviceService deviceService;

    private TrackDTOAssembler trackDTOAssembler;

    @Inject
    public DeviceTracksResource(DeviceService deviceService, TrackDTOAssembler trackDTOAssembler) {
        this.deviceService = deviceService;
        this.trackDTOAssembler = trackDTOAssembler;
    }

    @Timed
    @ApiOperation(
        value = "Get device tracks for a specific date",
        notes = "Gets all the tracks of the specified device and date",
        response = TrackDTO.class,
        responseContainer = "List"
    )
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getForDate(
        @ApiParam(value = "device to get tracks for", required = true) @PathVariable("device_id") Long deviceId,
        @ApiParam(value = "start date to filter by", required = false) @Valid @RequestParam(value = "fromDate", required = false) DateTime fromDate,
        @ApiParam(value = "end date to filter by", required = false) @Valid @RequestParam(value = "toDate", required = false) DateTime toDate
    ) {
        if (fromDate == null) fromDate = new DateTime().withTimeAtStartOfDay();
        if (toDate == null) toDate = fromDate.plusDays(1);

        List<Track> tracks = deviceService.getTracks(deviceId, fromDate, toDate);

        List<TrackDTO> trackDTOs = new ArrayList<>();
        for (Track track : tracks) {
            trackDTOs.add(trackDTOAssembler.toResource(track));
        }
        return new ResponseEntity<>(trackDTOs, HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(DateTime.class, new DateTimeEditor("yyyy-MM-dd", false));
    }
}
