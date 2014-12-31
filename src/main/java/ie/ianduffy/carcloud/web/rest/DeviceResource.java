package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.assembler.DeviceDTOAssembler;
import ie.ianduffy.carcloud.web.dto.DeviceDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * REST controller for managing devices.
 */
@Api(
    value = "device",
    description = "Device API"
)
@RestController
@RequestMapping("/app/rest/devices")
public class DeviceResource {

    @Inject
    private DeviceDTOAssembler deviceDTOAssembler;

    @Inject
    private DeviceService deviceService;

    @Timed
    @ApiOperation(
        value = "Create device",
        notes = "Creates a new device"
    )
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
        @ApiParam(value = "new device object") @Valid @RequestBody DeviceDTO deviceDTO
    ) {
        Device device = deviceService.create(deviceDTO);
        return new ResponseEntity<>(deviceDTOAssembler.toResource(device), HttpStatus.CREATED);
    }

    @Timed
    @ApiOperation(
        value = "Delete device",
        notes = "Deletes the specified device"
    )
    @RequestMapping(
        value = "/{device_id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(
        @ApiParam(value = "device to delete") @PathVariable("device_id") Long deviceId
    ) {
        deviceService.delete(deviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Timed
    @ApiOperation(
        value = "Get device",
        notes = "Gets the specified device"
    )
    @RequestMapping(
        value = "/{device_id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DeviceDTO> get(
        @ApiParam(value = "device to get") @PathVariable("device_id") Long deviceId
    ) {
        Device device = deviceService.findOneForCurrentUser(deviceId);
        return new ResponseEntity<>(deviceDTOAssembler.toResource(device), HttpStatus.OK);
    }

    @Timed
    @ApiOperation(
        value = "Get devices",
        notes = "Gets all devices"
    )
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAll() {
        List<Device> devices = deviceService.findAllForCurrentUser();
        List<DeviceDTO> deviceDTOs = new LinkedList<>();
        for (Device device : devices) {
            deviceDTOs.add(deviceDTOAssembler.toResource(device));
        }
        return new ResponseEntity<>(deviceDTOs, HttpStatus.OK);
    }

    @Timed
    @ApiOperation(
        value = "Update device",
        notes = "Updates the specified device"
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
        @ApiParam(value = "updated device object") @Valid @RequestBody DeviceDTO deviceDTO
    ) {
        Device device = deviceService.update(deviceDTO);
        return new ResponseEntity<>(deviceDTOAssembler.toResource(device), HttpStatus.OK);
    }
}
