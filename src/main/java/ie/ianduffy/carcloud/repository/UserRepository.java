package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    User findOneByUsernameOrEmailOrPhone(String user);

    List<User> findTop10ByUsernameLike(String username);
}
