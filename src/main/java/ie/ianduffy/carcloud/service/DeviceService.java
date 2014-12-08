package ie.ianduffy.carcloud.service;

import com.google.common.collect.Sets;
import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.DeviceRepository;
import ie.ianduffy.carcloud.web.rest.dto.DeviceDTO;
import ie.ianduffy.carcloud.web.rest.dto.UserDTO;
import org.dozer.Mapper;
import org.hibernate.StaleObjectStateException;
import org.hibernate.StaleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing devices.
 */
@Service
@Transactional
public class DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceService.class);

    @Inject
    private DeviceRepository deviceRepository;
    @Inject
    private Mapper mapper;
    @Inject
    private UserService userService;

    private Device create(DeviceDTO deviceDTO) {
        Device device = new Device();
        return setProperties(device, deviceDTO);
    }

    public Device createOrUpdate(DeviceDTO deviceDTO) {

        Device device = deviceRepository.findOne(deviceDTO.getId());

        if (device != null) {
            return update(device, deviceDTO);
        }

        return create(deviceDTO);
    }

    public List<DeviceDTO> findAllForCurrentUser() {
        UserDTO userDTO = userService.getUserWithAuthorities();
        User user = new User();
        mapper.map(userDTO, user);

        List<Device> devices = deviceRepository.findAllForCurrentUser(user);
        List<DeviceDTO> deviceDTOs = new ArrayList<>();
        for (Device device : devices) {
            DeviceDTO deviceDTO = new DeviceDTO();
            mapper.map(device, deviceDTO);
            deviceDTOs.add(deviceDTO);
        }
        return deviceDTOs;
    }

    public DeviceDTO findOneForCurrentUser(Long id) {
        UserDTO userDTO = userService.getUserWithAuthorities();
        User user = new User();
        mapper.map(userDTO, user);

        Device device = deviceRepository.findOneForCurrentUser(user, id);
        if (device == null) return null;
        DeviceDTO deviceDTO = new DeviceDTO();
        mapper.map(device, deviceDTO);
        return deviceDTO;
    }

    private Device setProperties(Device device, DeviceDTO deviceDTO) {
        if (deviceDTO.getOwners().size() <= 0) {
            UserDTO userDTO = userService.getUserWithAuthorities();
            User user = new User();
            mapper.map(userDTO, user);
            device.setOwners(Sets.newHashSet(user));
        }

        if(deviceDTO.getVersion() != device.getVersion()) {
            throw new StaleStateException("Unexpected version. Got " + deviceDTO.getVersion() + " expected " + device.getVersion());
        }

        mapper.map(deviceDTO, device);

        deviceRepository.save(device);
        return device;
    }

    private Device update(Device device, DeviceDTO deviceDTO) {
        UserDTO userDTO = userService.getUserWithAuthorities();
        User user = new User();
        mapper.map(userDTO, user);

        if (!device.getOwners().contains(user)) {
            return null;
        }

        return setProperties(device, deviceDTO);
    }
}
