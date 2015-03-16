package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import ie.ianduffy.carcloud.domain.Field;
import ie.ianduffy.carcloud.domain.FieldString;
import ie.ianduffy.carcloud.service.AlertService;
import ie.ianduffy.carcloud.web.dto.PairDTO;
import jdk.internal.util.xml.impl.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

/**
 * REST controller for managing Device.
 */
@Api(
    value = "alert field",
    description = "Alert Fields API"
)
@RestController
@RequestMapping("/app/rest/alerts/{alert_id}/fields")
public class AlertsFieldsResource {

    private AlertService alertService;

    @Inject
    public AlertsFieldsResource(AlertService alertService) {
        this.alertService = alertService;
    }

    @Timed
    @ApiOperation(
        value = "Add a field to a device alert",
        notes = "Adds a new field to a device alert"
    )
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void create(
        @ApiParam(value = "device to add alert to", required = true) @PathVariable("alert_id") Long alertId,
        @ApiParam(value = "field to add", required = true) @RequestBody PairDTO pair
    ) {
        alertService.addField(alertId, pair.getKey(), pair.getValue());
    }

    @Timed
    @ApiOperation(
        value = "Remove a field",
        notes = "Removes a field from the specified alert"
    )
    @RequestMapping(
        value = "/{field_id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void delete(
        @ApiParam(value = "alert to delete field from", required = true) @PathVariable("alert_id") Long alertId,
        @ApiParam(value = "field to remove", required = true) @PathVariable("field_id") Long fieldId
    ) {
        alertService.removeAlert(fieldId, alertId);
    }

    @Timed
    @ApiOperation(
        value = "Get alert fields",
        notes = "Gets all fields of the specified alert"
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
        @ApiParam(value = "alert to get fields for", required = true) @PathVariable("alert_id") Long alertId
    ) {
        return new ResponseEntity<>(alertService.getFields(alertId), HttpStatus.OK);
    }
}
