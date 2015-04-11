//package ie.ianduffy.carcloud.web.rest;
//
//import com.codahale.metrics.annotation.Timed;
//import com.wordnik.swagger.annotations.Api;
//import com.wordnik.swagger.annotations.ApiOperation;
//import com.wordnik.swagger.annotations.ApiParam;
//import ie.ianduffy.carcloud.domain.Alert;
//import ie.ianduffy.carcloud.domain.Device;
//import ie.ianduffy.carcloud.service.AlertService;
//import ie.ianduffy.carcloud.web.assembler.AlertDTOAssembler;
//import ie.ianduffy.carcloud.web.dto.AlertDTO;
//import ie.ianduffy.carcloud.web.dto.DeviceDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.inject.Inject;
//import javax.validation.Valid;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * REST controller for managing alerts.
// */
//@Api(
//    value = "alert",
//    description = "Alert API"
//)
//@RestController
//@RequestMapping("/app/rest/alerts")
//public class AlertsResource {
//
//    private AlertDTOAssembler alertDTOAssembler;
//
//    private AlertService alertService;
//
//    @Inject
//    public AlertsResource(AlertDTOAssembler alertDTOAssembler, AlertService alertService) {
//        this.alertDTOAssembler = alertDTOAssembler;
//        this.alertService = alertService;
//    }
//
//    @Timed
//    @ApiOperation(
//        value = "Delete alert",
//        notes = "Deletes the specified alert"
//    )
//    @RequestMapping(
//        value = "/{alert_id}",
//        method = RequestMethod.DELETE,
//        produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public void delete(
//        @ApiParam(value = "alert to be deleted", required = true) @PathVariable("alert_id") Long alertId
//    ) {
//        alertService.delete(alertId);
//    }
//
//    @Timed
//    @ApiOperation(
//        value = "Get alerts",
//        notes = "Gets the specified alerts",
//        response = AlertDTO.class
//    )
//    @RequestMapping(
//        value = "/{alert_id}",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<?> get(
//        @ApiParam(value = "alert to get", required = true) @PathVariable("alert_id") Long alertId
//    ) {
//        Alert alert = alertService.findOneForCurrentUserWithDevice(alertId);
//        if (alert == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(alertDTOAssembler.toResource(alert), HttpStatus.OK);
//    }
//
//    @Timed
//    @ApiOperation(
//        value = "Update device",
//        notes = "Updates the specified device",
//        response = AlertDTO.class
//    )
//    @RequestMapping(
//        method = RequestMethod.PUT,
//        produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<?> update(
//        @ApiParam(value = "updated alert object") @Valid @RequestBody AlertDTO alertDTO
//    ) {
//        Alert alert = alertService.update(alertDTO);
//        return new ResponseEntity<>(alertDTOAssembler.toResource(alert), HttpStatus.OK);
//    }
//
//    @Timed
//    @ApiOperation(
//        value = "Get all alerts",
//        notes = "Gets all alerts",
//        response = AlertDTO.class,
//        responseContainer = "List",
//        hidden = false
//    )
//    @RequestMapping(
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<?> getAll() {
//        List<Alert> alerts = alertService.findAllForCurrentUserWithDevice();
//        List<AlertDTO> alertDTOs = new LinkedList<>();
//        for (Alert alert : alerts) {
//            alertDTOs.add(alertDTOAssembler.toResource(alert));
//        }
//        return new ResponseEntity<>(alertDTOs, HttpStatus.OK);
//    }
//}
