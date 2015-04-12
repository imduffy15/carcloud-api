package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.service.AlertService;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.assembler.AlertDTOAssembler;
import ie.ianduffy.carcloud.web.dto.AlertDTO;
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
@Api(
    value = "device alerts",
    description = "Device Alerts API"
)
@RestController
@RequestMapping("/app/rest/devices/{device_id}/alerts")
public class DeviceAlertsResource {

    private AlertDTOAssembler alertDTOAssembler;
    private AlertService alertService;
    private DeviceService deviceService;

    @Inject
    public DeviceAlertsResource(DeviceService deviceService, AlertDTOAssembler alertDTOAssembler, AlertService alertService) {
        this.deviceService = deviceService;
        this.alertDTOAssembler = alertDTOAssembler;
        this.alertService = alertService;
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
    public ResponseEntity<?> create(
        @ApiParam(value = "device to add alert to", required = true) @PathVariable("device_id") Long deviceId,
        @ApiParam(value = "alert to add", required = true) @RequestBody AlertDTO alertDTO
    ) {
        return new ResponseEntity<>(alertDTOAssembler.toResource(deviceService.addAlert(deviceId, alertDTO)), HttpStatus.OK);
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
    public ResponseEntity<?> delete(
        @ApiParam(value = "device to delete alert from", required = true) @PathVariable("device_id") Long deviceId,
        @ApiParam(value = "alert to remove", required = true) @PathVariable("alert_id") Long alertId
    ) {
        Alert alert = alertService.findOneForCurrentUser(alertId);
        if (alert.getDevice().getId().equals(deviceId)) {
            alertService.delete(alertId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Timed
    @ApiOperation(
        value = "Get alert",
        notes = "Gets the specified alert",
        response = AlertDTO.class
    )
    @RequestMapping(
        value = "/{alert_id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> get(
        @ApiParam(value = "device the alert belongs to", required = true) @PathVariable("device_id") Long deviceId,
        @ApiParam(value = "alert to get", required = true) @PathVariable("alert_id") Long alertId
    ) {
        Alert alert = alertService.findOneForCurrentUser(alertId);
        if (alert.getDevice().getId().equals(deviceId)) {
            return new ResponseEntity<>(alertDTOAssembler.toResource(alert), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Timed
    @ApiOperation(
        value = "Get device alerts",
        notes = "Gets all alerts of the specified device"
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

    @Timed
    @ApiOperation(
        value = "Updates a device alert",
        notes = "updates the specified alert"
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
        @ApiParam(value = "updated alert object") @Valid @RequestBody AlertDTO alertDTO
    ) {
        Alert alert = deviceService.updateAlert(alertDTO);
        return new ResponseEntity<>(alertDTOAssembler.toResource(alert), HttpStatus.OK);
    }
}
