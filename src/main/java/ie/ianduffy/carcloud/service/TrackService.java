package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.repository.RestrictedRepository;
import ie.ianduffy.carcloud.repository.TrackRepository;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class TrackService extends AbstractRestrictedService<Track, Long, TrackDTO> {

    @Inject
    private TrackRepository trackRepository;

    @Transactional(readOnly = true)
    public List<Track> findAllForCurrentUserWithDevice() {
        List<Track> tracks = findAllForCurrentUser();
        for (Track track : tracks) Hibernate.initialize(track.getDevice());
        return tracks;
    }

    @Transactional(readOnly = true)
    public Track findOneForCurrentUserWithDevice(Long id) {
        Track track = findOneForCurrentUser(id);
        Hibernate.initialize(track.getDevice());
        return track;
    }

    @Override
    protected RestrictedRepository<Track, Long> getRepository() {
        return trackRepository;
    }

}
