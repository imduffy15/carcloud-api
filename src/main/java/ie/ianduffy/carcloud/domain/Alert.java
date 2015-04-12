package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "T_ALERT")
@ToString(exclude = {"device", "fields"})
@NamedQueries({
    @NamedQuery(name = "Alert.findAllForUser", query = "select a from Alert a where ?1 MEMBER OF a.device.owners"),
    @NamedQuery(name = "Alert.findOneForUser", query = "select a from Alert a where a.id = ?2 and ?1 MEMBER OF a.device.owners"),
})
@EqualsAndHashCode(exclude = {"device", "fields"}, callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Alert extends AbstractAuditingEntity<Long> implements Serializable {

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    private LocalTime after;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    private LocalTime before;
    @Size(min = 1, max = 150)
    @Column(name = "description", length = 150)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;
    @JoinTable(
        name = "T_ALERT_FIELD",
        joinColumns = {@JoinColumn(name = "alert_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "field_id", referencedColumnName = "id")})
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.ALL})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<AlertFieldWrapper> fields;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public Alert() {
    }

    public Alert(Device device, String description, LocalTime after, LocalTime before, List<AlertFieldWrapper> fields) {
        this.device = device;
        this.description = description;
        this.after = after;
        this.before = before;
        this.fields = fields;
    }
}
