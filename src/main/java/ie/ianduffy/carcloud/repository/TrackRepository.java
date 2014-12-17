package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.PersistentAuditEvent;
import ie.ianduffy.carcloud.domain.Track;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Track entity.
 */
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("select p from Track p where p.recordedAt >= ?1 and p.recordedAt <= ?2")
    List<Track> findByDates(LocalDateTime fromDate, LocalDateTime toDate);

}
