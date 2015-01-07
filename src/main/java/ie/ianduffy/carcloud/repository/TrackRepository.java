package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Track;

/**
 * Spring Data JPA repository for the Track entity.
 */
public interface TrackRepository extends RestrictedRepository<Track, Long> {

}
