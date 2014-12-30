package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.repository.TrackRepository;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import ie.ianduffy.carcloud.web.munic.dto.EventDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

/**
 * Service class for managing tracks.
 */
@Service
@Transactional
public class TrackService extends AbstractService<Track, Long, TrackDTO> {

    @Inject
    DeviceService deviceService;

    @Inject
    private TrackRepository trackRepository;

    public void create(ie.ianduffy.carcloud.web.munic.dto.TrackDTO trackDTO) {
        Track track = new Track(
            deviceService.findOne(trackDTO.getDeviceId()),
            trackDTO.getLocation(),
            trackDTO.getReceivedAt(),
            trackDTO.getRecordedAt()
        );
        trackRepository.save(track);
    }

    @Transactional(readOnly = true)
    public List<Track> findAllForCurrentUser() {
        return trackRepository.findAllForUser(SecurityUtils.getCurrentLogin());
    }

    @Transactional(readOnly = true)
    public Track findOneForCurrentUser(Long id) {
        Track track = trackRepository.findOneForUser(SecurityUtils.getCurrentLogin(), id);
        if (track == null) {
            throw new EntityNotFoundException();
        }
        return track;
    }

    @Override
    protected JpaRepository<Track, Long> getRepository() {
        return trackRepository;
    }
}
