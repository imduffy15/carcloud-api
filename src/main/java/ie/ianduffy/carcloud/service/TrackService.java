package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.repository.TrackRepository;
import ie.ianduffy.carcloud.web.munic.dto.TrackDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * Service class for managing tracks.
 */
@Service
@Transactional
public class TrackService {

    @Inject
    DeviceService deviceService;

    @Inject
    private TrackRepository trackRepository;

    @Inject
    private UserService userService;

    public void create(List<TrackDTO> trackDTOs) {
        for (TrackDTO trackDTO : trackDTOs) {
            Track track = new Track(
                deviceService.findOne(trackDTO.getPayload().getDeviceId()),
                trackDTO.getPayload().getLocation(),
                trackDTO.getPayload().getReceivedAt(),
                trackDTO.getPayload().getRecordedAt()
            );
            trackRepository.save(track);
        }
    }

    public void delete(Long id) {
        trackRepository.delete(id);
    }

    public List<Track> findAllForCurrentUser() {
        return trackRepository.findAllForCurrentUser(userService.getUser());
    }

    public Track findOneForCurrentUser(Long id) {
        return trackRepository.findOneForCurrentUser(userService.getUser(), id);
    }
}
