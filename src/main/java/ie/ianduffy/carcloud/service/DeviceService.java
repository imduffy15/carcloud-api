package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.DeviceRepository;
import ie.ianduffy.carcloud.web.dto.DeviceDTO;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import org.dozer.Mapper;
import org.hibernate.StaleStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Service class for managing devices.
 */
@Service
@Transactional
public class DeviceService {

    @Inject
    private DeviceRepository deviceRepository;
    @Inject
    private Mapper mapper;
    @Inject
    private UserService userService;

    public Device addOwner(Long id, String owner) {
        Device device = findOneForCurrentUser(id);
        List<User> owners = device.getOwners();
        owners.add(userService.getUser(owner));
        device.setOwners(owners);

        deviceRepository.save(device);
        return device;
    }

    public Device addTrack(Long id, TrackDTO trackDTO) {
        Device device = findOneForCurrentUser(id);
        List<Track> tracks = device.getTracks();
        Track track = new Track();

        mapper.map(trackDTO, track);

        tracks.add(track);
        device.setTracks(tracks);

        deviceRepository.save(device);
        return device;
    }

    private Device create(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setOwners(Arrays.asList(userService.getUser()));

        mapper.map(deviceDTO, device);
        deviceRepository.save(device);
        return device;
    }

    public Device createOrUpdate(DeviceDTO deviceDTO) {

        Device device = deviceRepository.findOne(deviceDTO.getId());

        if (device != null) {
            return update(device, deviceDTO);
        }

        return create(deviceDTO);
    }

    public List<Device> findAllForCurrentUser() {
        User user = userService.getUser();

        return deviceRepository.findAllForCurrentUser(user);
    }

    public Device findOneForCurrentUser(Long id) {
        User user = userService.getUser();

        Device device = deviceRepository.findOneForCurrentUser(user, id);
        if (device == null) return null;
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

    public void removeTrack(Long id, int index) {
        Device device = findOneForCurrentUser(id);
        List<Track> tracks = device.getTracks();
        tracks.remove(index);
        device.setTracks(tracks);
        deviceRepository.save(device);
    }

    public void delete(Long id) {
        deviceRepository.delete(id);
    }

    private Device update(Device device, DeviceDTO deviceDTO) {
        User user = userService.getUser();
        deviceDTO.setId(null);

        if (!device.getOwners().contains(user)) {
            return null;
        }

        if (deviceDTO.getVersion() != device.getVersion()) {
            throw new StaleStateException("Unexpected version. Got " + deviceDTO.getVersion() + " expected " + device.getVersion());
        }

        mapper.map(deviceDTO, device);

        deviceRepository.save(device);
        return device;
    }
}
