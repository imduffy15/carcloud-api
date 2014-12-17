package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.repository.TrackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service class for managing tracks.
 */
@Service
@Transactional
public class TrackService {

    @Inject
    private TrackRepository trackRepository;

    public void delete(Long id) {
        trackRepository.delete(id);
    }

    public List<Track> findAll() {
        return trackRepository.findAll();
    }

    public Track findOne(Long id) {
        return trackRepository.findOne(id);
    }
}
