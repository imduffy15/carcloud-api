package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.domain.User;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Track entity.
 */
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("select t from Track t where :#{#user} MEMBER OF t.device.owners")
    List<Track> findAllForUser(@Param("user") String user);

    @Query("select t from Track t where t.recordedAt >= ?1 and t.recordedAt <= ?2")
    List<Track> findByDates(LocalDateTime fromDate, LocalDateTime toDate);

    @Query("select t from Track t where t.id = :#{#id} and :#{#user} MEMBER OF t.device.owners")
    Track findOneForUser(@Param("user") String user, @Param("id") Long id);

}
