package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.domain.User;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findTop10ByUsernameLike(String username);
}
