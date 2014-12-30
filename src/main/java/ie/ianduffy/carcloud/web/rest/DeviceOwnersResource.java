package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * REST controller for managing a device's owners.
 */
@RestController
@RequestMapping("/app/rest/devices/{device_id}/owners")
public class DeviceOwnersResource {

    @Inject
    private DeviceService deviceService;

    @Inject
    private UserDTOAssembler userDTOAssembler;

    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@PathVariable("device_id") Long deviceId, @Valid @RequestBody String username) {
        deviceService.addOwner(deviceId, username);
    }

    @RequestMapping(value = "/{username}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable("device_id") Long deviceId, @PathVariable("username") String username) {
        deviceService.removeOwner(deviceId, username);
    }

    @RequestMapping(value = "/{username}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> get(@PathVariable("device_id") Long deviceId,
                                 @PathVariable("username") String username) {
        return new ResponseEntity<>(
            userDTOAssembler.toResource(deviceService.getOwner(deviceId, username)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAll(@PathVariable("device_id") Long deviceId) {
        Map<String, UserDTO> owners = new HashMap<>();
        for (User user : deviceService.getOwners(deviceId)) {
            owners.put(user.getUsername(), userDTOAssembler.toResource(user));
        }
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }
}
