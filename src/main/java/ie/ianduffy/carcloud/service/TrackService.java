package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.repository.RestrictedRepository;
import ie.ianduffy.carcloud.repository.TrackRepository;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service class for managing tracks.
 */
@Service
@Transactional
public class TrackService extends AbstractRestrictedService<Track, Long, TrackDTO> {

    @Inject
    private TrackRepository trackRepository;

    @Override
    protected RestrictedRepository<Track, Long> getRepository() {
        return trackRepository;
    }
}
