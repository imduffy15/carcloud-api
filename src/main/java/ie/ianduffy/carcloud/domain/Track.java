package ie.ianduffy.carcloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javadocmd.simplelatlng.LatLng;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * A Track.
 */
@Entity
@Table(name = "T_TRACK")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Track implements Serializable {

    @OneToOne
    private Device device;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Double latitude;
    private Double longitude;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime receivedAt;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime recordedAt;

    public Track() {

    }

    public Track(Device device, LatLng location, DateTime receivedAt, DateTime recordedAt) {
        this.device = device;
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.receivedAt = receivedAt;
        this.recordedAt = recordedAt;
    }

    public Track(Device device, List<Double> location, DateTime receivedAt, DateTime recordedAt) {
        this.device = device;
        this.longitude = location.get(0);
        this.latitude = location.get(1);
        this.receivedAt = receivedAt;
        this.recordedAt = recordedAt;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public LatLng getLocation() {
        return new LatLng(latitude, longitude);
    }

    public void setLocation(LatLng location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public DateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(DateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public DateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(DateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Track track = (Track) o;

        if (id != track.id) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Track{" +
            "id=" + id +
            '}';
    }
}
