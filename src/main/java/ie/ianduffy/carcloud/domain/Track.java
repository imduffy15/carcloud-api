package ie.ianduffy.carcloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javadocmd.simplelatlng.LatLng;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
@Data
@Entity
@Table(name = "T_TRACK")
@ToString(exclude = {"device"})
@EqualsAndHashCode(exclude = {"device"}, callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Track implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Double latitude;
    private Double longitude;

    @Column(name = "received_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime receivedAt;

    @Column(name = "recorded_at")
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

    @JsonIgnore
    public LatLng getLocation() {
        return new LatLng(latitude, longitude);
    }

    public void setLocation(LatLng location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

}
