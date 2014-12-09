package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.assembler.UserDTOAssembler;
import ie.ianduffy.carcloud.web.dto.UserDTO;
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
@RestController
@RequestMapping("/app/rest/devices/{id}/owners")
public class DeviceOwnersResource {

    @Inject
    private DeviceService deviceService;
    @Inject
    private UserDTOAssembler userDTOAssembler;

    /**
     * POST  /rest/devices/:id/owners -> Create a new owner.
     */
    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@PathVariable("id") Long id, @Valid @RequestBody String owner) {
        deviceService.addOwner(id, owner);
    }

    /**
     * DELETE  /rest/devices/:id/owners/:index -> delete the owner.
     */
    @RequestMapping(value = "/{index}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable("id") Long id, @PathVariable("index") int index) {
        deviceService.removeOwner(id, index);
    }

    /**
     * GET  /rest/devices/:id/owner/:index -> get the "id" device.
     */
    @RequestMapping(value = "/{index}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> get(@PathVariable("id") Long id, @PathVariable("index") int index) {
        Device device = deviceService.findOneForCurrentUser(id);
        if (device.getOwners() != null) {
            return new ResponseEntity<>(userDTOAssembler.toResource(device.getOwners().get(index)), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * GET  /rest/devices -> get all the devices.
     */
    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAll(@PathVariable("id") Long id) {
        Device device = deviceService.findOneForCurrentUser(id);
        List<UserDTO> owners = new ArrayList<>();
        if (device.getOwners() != null) {
            for (User user : device.getOwners()) {
                owners.add(userDTOAssembler.toResource(user));
            }
        }
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }
}
