package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.assembler.UserDTOAssembler;
import ie.ianduffy.carcloud.web.dto.UserDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * REST controller for managing a device's owners.
 */
@Api(
    value = "device owners",
    description = "Device Owners API"
)
@RestController
@RequestMapping("/app/rest/devices/{device_id}/owners")
public class DeviceOwnersResource {

    private DeviceService deviceService;

    private UserDTOAssembler userDTOAssembler;

    @Inject
    public DeviceOwnersResource(DeviceService deviceService, UserDTOAssembler userDTOAssembler) {
        this.deviceService = deviceService;
        this.userDTOAssembler = userDTOAssembler;
    }

    @Timed
    @ApiOperation(
        value = "Add a device owner",
        notes = "Adds a new owner to the specified device"
    )
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void create(
        @ApiParam(value = "device to add owner to", required = true) @PathVariable("device_id") Long deviceId,
        @ApiParam(value = "user to add as an owner", required = true) @RequestBody String username
    ) {
        deviceService.addOwner(deviceId, username);
    }

    @Timed
    @ApiOperation(
        value = "Remove a device owner",
        notes = "Removes a owner from the specified device"
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void delete(
        @ApiParam(value = "device to delete owner from", required = true) @PathVariable("device_id") Long deviceId,
        @ApiParam(value = "user to remove as an owner", required = true) @PathVariable("username") String username
    ) {
        deviceService.removeOwner(deviceId, username);
    }

    @Timed
    @ApiOperation(
        value = "Get device owners",
        notes = "Gets all owners of the specified device"
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
        @ApiParam(value = "device to get owners for", required = true) @PathVariable("device_id") Long deviceId
    ) {
        Map<String, UserDTO> owners = new HashMap<>();
        for (User user : deviceService.getOwners(deviceId)) {
            owners.put(user.getUsername(), userDTOAssembler.toResource(user));
        }
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }
}
