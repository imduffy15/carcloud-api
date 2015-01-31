package ie.ianduffy.carcloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javadocmd.simplelatlng.LatLng;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A Track.
 */
@Data
@Entity
@NamedQueries({
    @NamedQuery(name = "Track.findAllForUser", query = "select t from Track t where ?1 MEMBER OF t.device.owners"),
    @NamedQuery(name = "Track.findOneForUser", query = "select t from Track t where t.id = ?2 and ?1 MEMBER OF t.device.owners")
})
@Table(name = "T_TRACK")
@ToString(exclude = {"device"})
@EqualsAndHashCode(exclude = {"device"}, callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Track extends AbstractAuditingEntity<Long> implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "received_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime receivedAt;

    @Column(name = "recorded_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime recordedAt;

    @JoinTable(
        name = "T_TRACK_FIELD",
        joinColumns = {@JoinColumn(name = "track_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "field_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Field> fields = new ArrayList<>();


    public Track() {
    }

    public Track(Device device, List<Field> fields, LatLng location, DateTime receivedAt, DateTime recordedAt) {
        this.device = device;
        this.fields = fields;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.receivedAt = receivedAt;
        this.recordedAt = recordedAt;
    }
}
