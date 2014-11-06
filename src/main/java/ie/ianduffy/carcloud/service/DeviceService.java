package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceService.class);

    @Inject
    private UserService userService;

    @Inject
    private DeviceRepository deviceRepository;

    public Device create(Device device) {
        Device newDevice;
        if(device.getId() != null) {
            newDevice = deviceRepository.save(device);
        } else {
            newDevice = new Device();
            newDevice.setDescription(device.getDescription());
            Set<User> owners = new HashSet<>();
            owners.add(userService.getUserWithAuthorities());
            newDevice.setOwners(owners);
            deviceRepository.save(newDevice);
        }
        return newDevice;
    }
}
