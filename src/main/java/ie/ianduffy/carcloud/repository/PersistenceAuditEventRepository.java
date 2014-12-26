package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.PersistentAuditEvent;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository
    extends JpaRepository<PersistentAuditEvent, String> {

    @Query("select p from PersistentAuditEvent p where p.auditEventDate >= ?1 and p.auditEventDate <= ?2")
    List<PersistentAuditEvent> findByDates(LocalDateTime fromDate, LocalDateTime toDate);

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateGreaterThan(String principal,
                                                                           LocalDateTime after);
}
