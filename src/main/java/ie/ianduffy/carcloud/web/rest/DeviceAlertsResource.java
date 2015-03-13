package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.assembler.AlertDTOAssembler;
import ie.ianduffy.carcloud.web.dto.AlertDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing Device.
 */
@Api(
    value = "device alerts",
    description = "Device Alerts API"
)
@RestController
@RequestMapping("/app/rest/devices/{device_id}/alerts")
public class DeviceAlertsResource {

    private AlertDTOAssembler alertDTOAssembler;
    private DeviceService deviceService;

    @Inject
    public DeviceAlertsResource(DeviceService deviceService, AlertDTOAssembler alertDTOAssembler) {
        this.deviceService = deviceService;
        this.alertDTOAssembler = alertDTOAssembler;
    }

    @Timed
    @ApiOperation(
        value = "Add a device alert",
        notes = "Adds a new alert to the specified device"
    )
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void create(
        @ApiParam(value = "device to add alert to", required = true) @PathVariable("device_id") Long deviceId,
        @ApiParam(value = "alert to add", required = true) @RequestBody AlertDTO alertDTO
    ) {
        deviceService.addAlert(deviceId, alertDTO);
    }

    @Timed
    @ApiOperation(
        value = "Remove a device alert",
        notes = "Removes a alert from the specified device"
    )
    @RequestMapping(
        value = "/{alert_id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void delete(
        @ApiParam(value = "device to delete owner from", required = true) @PathVariable("device_id") Long deviceId,
        @ApiParam(value = "alert to remove", required = true) @PathVariable("alert_id") Long alertId
    ) {
        deviceService.removeAlert(deviceId, alertId);
    }

    @Timed
    @ApiOperation(
        value = "Get device alerts",
        notes = "Gets all alerts of the specified device"
//        Unsupported until swagger 1.5.0
//        response = UserDTO.class,
//        responseContainer = "Map",
//        responseKey = String.class
    )
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAll(
        @ApiParam(value = "device to get alerts for", required = true) @PathVariable("device_id") Long deviceId
    ) {
        List<AlertDTO> alerts = new ArrayList<>();
        for (Alert alert : deviceService.getAlerts(deviceId)) {
            alerts.add(alertDTOAssembler.toResource(alert));
        }
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }
}
