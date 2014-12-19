package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Authority;
import ie.ianduffy.carcloud.domain.PersistentAuditEvent;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
