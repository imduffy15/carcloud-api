package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;

import ie.ianduffy.carcloud.assembler.DeviceDTOAssembler;
import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.web.dto.DeviceDTO;
import ie.ianduffy.carcloud.service.DeviceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RestController
@RequestMapping("/app/rest/devices")
public class DeviceResource {

    @Inject
    private DeviceDTOAssembler deviceDTOAssembler;

    @Inject
    private DeviceService deviceService;

    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> create(@Valid @RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.createOrUpdate(deviceDTO);
        return new ResponseEntity<>(deviceDTOAssembler.toResource(device), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{device_id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> delete(@PathVariable("device_id") Long deviceId) {
        deviceService.delete(deviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{device_id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeviceDTO> get(@PathVariable("device_id") Long deviceId) {
        Device device = deviceService.findOneForCurrentUser(deviceId);
        return new ResponseEntity<>(deviceDTOAssembler.toResource(device), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAll() {
        List<Device> devices = deviceService.findAllForCurrentUser();
        List<DeviceDTO> deviceDTOs = new LinkedList<>();
        for (Device device : devices) {
            deviceDTOs.add(deviceDTOAssembler.toResource(device));
        }
        return new ResponseEntity<>(deviceDTOs, HttpStatus.OK);
    }
}
