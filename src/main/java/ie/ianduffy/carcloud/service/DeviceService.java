package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.DeviceRepository;
import ie.ianduffy.carcloud.repository.RestrictedRepository;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.web.dto.DeviceDTO;
import ie.ianduffy.carcloud.web.munic.dto.TrackDTO;

import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

/**
 * Service class for managing devices.
 */
@Service
@Transactional
public class DeviceService extends AbstractRestrictedService<Device, Long, DeviceDTO> {

    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    private UserService userService;

    public void addTrack(TrackDTO trackDTO) {
        Device device = findOne(trackDTO.getDeviceId());
        device.getTracks().add(new Track(
           device,
           trackDTO.getLocation(),
           trackDTO.getReceivedAt(),
           trackDTO.getRecordedAt()
        ));
        deviceRepository.save(device);
    }

    public Device addOwner(Long id, String username) {
        User user = userService.findOne(username);
        Device device = findOneForCurrentUser(id);
        List<User> owners = device.getOwners();
        owners.add(user);
        deviceRepository.save(device);
        return device;
    }

    public Device create(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setOwners(Arrays.asList(userService.findOne(SecurityUtils.getCurrentLogin())));
        return super.create(deviceDTO, device);
    }

    @Override
    public void delete(Long id) {
        findOneForCurrentUser(id);
        super.delete(id);
    }

    @Override
    protected RestrictedRepository<Device, Long> getRepository() {
        return deviceRepository;
    }

    @Transactional(readOnly = true)
    public User getOwner(Long id, String username) {
        User user = userService.findOne(username);
        Device device = findOneForCurrentUser(id);
        return device.getOwners().get(device.getOwners().indexOf(user));
    }

    @Transactional(readOnly = true)
    public List<User> getOwners(Long id) {
        Device device = findOneForCurrentUser(id);
        List<User> owners = device.getOwners();
        Hibernate.initialize(owners);
        return owners;
    }

    @Transactional(readOnly = true)
    public Track getTrack(Long id, int index) {
        Device device = findOneForCurrentUser(id);
        return device.getTracks().get(index);
    }

    @Transactional(readOnly = true)
    public List<Track> getTracks(Long id) {
        Device device = findOneForCurrentUser(id);
        List<Track> tracks = device.getTracks();
        Hibernate.initialize(tracks);
        return tracks;
    }

    public void removeOwner(Long id, String username) {
        User user = userService.findOne(username);
        Device device = findOneForCurrentUser(id);
        List<User> owners = device.getOwners();
        owners.remove(owners.indexOf(user));
        deviceRepository.save(device);
    }

    public Device update(DeviceDTO deviceDTO) {
        Device device = findOneForCurrentUser(deviceDTO.getId());
        return super.update(deviceDTO, device);
    }
}
