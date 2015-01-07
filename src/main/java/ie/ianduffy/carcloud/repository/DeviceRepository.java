package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Device;

/**
 * Spring Data JPA repository for the Device entity.
 */
public interface DeviceRepository extends RestrictedRepository<Device, Long> {

}
