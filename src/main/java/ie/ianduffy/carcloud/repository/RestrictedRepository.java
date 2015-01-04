package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.Track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface RestrictedRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    List<T> findAllForUser(String user);
    T findOneForUser(String user, ID id);
}
