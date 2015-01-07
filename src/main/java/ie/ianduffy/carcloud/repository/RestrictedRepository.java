package ie.ianduffy.carcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface RestrictedRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    List<T> findAllForUser(String user);

    T findOneForUser(String user, ID id);
}
