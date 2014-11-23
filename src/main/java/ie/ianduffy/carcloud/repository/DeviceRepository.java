package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Device entity.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query("select d from Device d where :#{#user.email} MEMBER OF d.owners")
    List<Device> findDevicesForCurrentUser(@Param("user") User user);

}
