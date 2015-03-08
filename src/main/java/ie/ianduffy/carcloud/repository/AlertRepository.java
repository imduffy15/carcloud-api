package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Alert entity.
 */
public interface AlertRepository extends RestrictedRepository<Alert, Long> {

}
