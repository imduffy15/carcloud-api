package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Alert;

/**
 * Spring Data JPA repository for the Alert entity.
 */
public interface AlertRepository extends RestrictedRepository<Alert, Long> {

}
