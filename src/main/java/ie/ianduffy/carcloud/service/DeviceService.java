package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.DeviceRepository;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.web.dto.DeviceDTO;
import ie.ianduffy.carcloud.web.dto.TrackDTO;

import org.dozer.Mapper;
import org.springframework.dao.OptimisticLockingFailureException;
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
public class DeviceService extends AbstractService<Device, Long, DeviceDTO> {

    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    private UserService userService;

    public Device addOwner(Long id, String owner) {
        Device device = findOneForCurrentUser(id);
        List<User> owners = device.getOwners();
        owners.add(userService.findOne(owner));
        device.setOwners(owners);

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
    protected JpaRepository<Device, Long> getRepository() {
        return deviceRepository;
    }

    public List<Device> findAllForCurrentUser() {
        return deviceRepository.findAllForUser(SecurityUtils.getCurrentLogin());
    }

    public Device findOneForCurrentUser(Long id) {
        Device device = deviceRepository.findOneForUser(SecurityUtils.getCurrentLogin(), id);
        if (device == null) {
            throw new EntityNotFoundException();
        }
        return device;
    }

    public User getOwner(Long id, int index) {
        Device device = findOneForCurrentUser(id);
        return device.getOwners().get(index);
    }

    public List<User> getOwners(Long id) {
        Device device = findOneForCurrentUser(id);
        return device.getOwners();
    }

    public Track getTrack(Long id, int index) {
        Device device = findOneForCurrentUser(id);
        return device.getTracks().get(index);
    }

    public List<Track> getTracks(Long id) {
        Device device = findOneForCurrentUser(id);
        device.getTracks().size();
        List<Track> tracks = device.getTracks();
        return tracks;
    }

    public void removeOwner(Long id, int index) {
        Device device = findOneForCurrentUser(id);
        List<User> owners = device.getOwners();
        owners.remove(index);
        device.setOwners(owners);
        deviceRepository.save(device);
    }

    public Device update(DeviceDTO deviceDTO) {
        Device device = findOneForCurrentUser(deviceDTO.getId());
        return super.update(deviceDTO, device);
    }
}
