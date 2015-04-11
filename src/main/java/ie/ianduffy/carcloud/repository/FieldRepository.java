package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.domain.Field;
import ie.ianduffy.carcloud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {

}
