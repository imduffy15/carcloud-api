package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.DeviceRepository;
import ie.ianduffy.carcloud.repository.RestrictedRepository;
import ie.ianduffy.carcloud.repository.TrackRepository;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.web.dto.AlertDTO;
import ie.ianduffy.carcloud.web.dto.DeviceDTO;
import ie.ianduffy.carcloud.web.munic.dto.TrackDTO;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class DeviceService extends AbstractRestrictedService<Device, Long, DeviceDTO> {

    @Inject
    private AlertService alertService;
    @Inject
    private DeviceRepository deviceRepository;
    @Inject
    private TrackRepository trackRepository;
    @Inject
    private UserService userService;

    public Alert addAlert(Long id, AlertDTO alertDTO) {
        Device device = findOneForCurrentUser(id);
        Alert alert = new Alert(device, alertDTO.getDescription(), alertDTO.getAfter(), alertDTO.getBefore(), alertDTO.getFields());
        List<Alert> alerts = device.getAlerts();
        alerts.add(alert);
        deviceRepository.save(device);
        return alert;
    }

    public Device addOwner(Long id, String username) {
        User user = userService.findOne(username);
        Device device = findOneForCurrentUser(id);
        Set<User> owners = device.getOwners();
        owners.add(user);
        deviceRepository.save(device);
        return device;
    }

    public Track addTrack(TrackDTO trackDTO) {
        Device device = findOne(trackDTO.getDeviceId());

        Track track = new Track(
            device,
            trackDTO.getFields(),
            trackDTO.getLocation(),
            trackDTO.getReceivedAt(),
            trackDTO.getRecordedAt()
        );

        device.getTracks().add(track);

        deviceRepository.save(device);

        return track;
    }

    public Device create(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setOwners(new HashSet(Arrays.asList(userService.findOne(SecurityUtils.getCurrentLogin()))));
        return super.create(deviceDTO, device);
    }

    @Override
    public void delete(Long id) {
        findOneForCurrentUser(id);
        super.delete(id);
    }

    @Transactional(readOnly = true)
    public List<Alert> getAlerts(Long id) {
        Device device = findOneForCurrentUser(id);
        List<Alert> alerts = device.getAlerts();
        Hibernate.initialize(alerts);
        return alerts;
    }

    @Transactional(readOnly = true)
    public Set<User> getOwners(Long id) {
        Device device = findOneForCurrentUser(id);
        Set<User> owners = device.getOwners();
        Hibernate.initialize(owners);
        return owners;
    }

    @Override
    protected RestrictedRepository<Device, Long> getRepository() {
        return deviceRepository;
    }

    @Transactional(readOnly = true)
    public List<Track> getTracks(Long id, DateTime fromDate, DateTime toDate) {
        Device device = findOneForCurrentUser(id);
        return trackRepository.findAllForDeviceByDate(device, fromDate, toDate);
    }

    public void removeOwner(Long id, String username) {
        User user = userService.findOne(username);
        Device device = findOneForCurrentUser(id);
        Set<User> owners = device.getOwners();
        owners.remove(user);
        deviceRepository.save(device);
    }

    public Device update(DeviceDTO deviceDTO) {
        Device device = findOneForCurrentUser(deviceDTO.getId());
        return super.update(deviceDTO, device);
    }
}
