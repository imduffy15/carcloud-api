package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Track entity.
 */
public interface TrackRepository extends RestrictedRepository<Track, Long> {

}
