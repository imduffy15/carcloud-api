package ie.ianduffy.carcloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javadocmd.simplelatlng.LatLng;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A Track.
 */
@Data
@Entity
@Table(name = "T_TRACK")
@ToString(exclude = {"device"})
@EqualsAndHashCode(exclude = {"device"}, callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Track extends AbstractAuditingEntity<Long> implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
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
