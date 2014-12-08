package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.repository.DeviceRepository;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.rest.dto.DeviceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for managing Device.
 */
@RestController
@RequestMapping("/app")
public class DeviceResource {

    private final Logger log = LoggerFactory.getLogger(DeviceResource.class);
    @Inject
    private DeviceRepository deviceRepository;
    @Inject
    private DeviceService deviceService;

    /**
     * POST  /rest/devices -> Create a new device.
     */
    @RequestMapping(value = "/rest/devices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> create(@Valid @RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.createOrUpdate(deviceDTO);
        if (device == null) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * DELETE  /rest/devices/:id -> delete the "id" device.
     */
    @RequestMapping(value = "/rest/devices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable("id") Long id) {
        log.debug("REST request to delete Device : {}", id);
        deviceRepository.delete(id);
    }

    /**
     * GET  /rest/devices/:id -> get the "id" device.
     */
    @RequestMapping(value = "/rest/devices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeviceDTO> get(@PathVariable("id") Long id) {
        log.debug("REST request to get Device : {}", id);
        DeviceDTO device = deviceService.findOneForCurrentUser(id);
        if (device == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    /**
     * GET  /rest/devices -> get all the devices.
     */
    @RequestMapping(value = "/rest/devices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeviceDTO> getAll() {
        log.debug("REST request to get all Devices");
        return deviceService.findAllForCurrentUser();
    }
}
