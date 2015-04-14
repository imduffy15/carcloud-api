package ie.ianduffy.carcloud.repository;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.Track;
import org.joda.time.DateTime;

import java.util.List;

public interface TrackRepository extends RestrictedRepository<Track, Long> {

    List<Track> findAllForDeviceByDate(Device device, DateTime fromDate, DateTime toDate);
}
