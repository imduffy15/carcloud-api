package ie.ianduffy.carcloud.domain;

import com.javadocmd.simplelatlng.LatLng;
import ie.ianduffy.carcloud.domain.eventlisteners.AlertSender;
import ie.ianduffy.carcloud.domain.eventlisteners.TrackPublisher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * A Track.
 */
@Data
@Entity
@NamedQueries({
    @NamedQuery(name = "Track.findAllForUser", query = "select t from Track t where ?1 MEMBER OF t.device.owners"),
    @NamedQuery(name = "Track.findOneForUser", query = "select t from Track t where t.id = ?2 and ?1 MEMBER OF t.device.owners"),
    @NamedQuery(name = "Track.findAllForDeviceByDate", query = "select t from Track t where t.device = ?1 and t.recordedAt >= ?2 and t.recordedAt < ?3")
})
@Table(name = "T_TRACK", indexes = {
    @Index(columnList = "recorded_at")
})
@ToString(exclude = {"device"})
@EqualsAndHashCode(exclude = {"device"}, callSuper = false)
@EntityListeners({TrackPublisher.class, AlertSender.class})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Track extends AbstractAuditingEntity<Long> implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @JoinTable(
        name = "T_TRACK_FIELD",
        joinColumns = {@JoinColumn(name = "track_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "field_id", referencedColumnName = "id")})
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.PERSIST})
    private List<Field> fields;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "latitude", nullable = false, updatable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false, updatable = false)
    private Double longitude;

    @NotNull
    @Column(name = "received_at", nullable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime receivedAt;

    @NotNull
    @Column(name = "recorded_at", nullable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime recordedAt;


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
