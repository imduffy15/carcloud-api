package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, String> {

    User findOneByEmail(String email);
}
