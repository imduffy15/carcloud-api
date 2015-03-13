package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;
    @LazyCollection(LazyCollectionOption.EXTRA)
    @JoinTable(
        name = "T_ALERT_FIELD",
        joinColumns = {@JoinColumn(name = "alert_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "field_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Field> fields = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public Alert() {
    }

    public Alert(Device device) {
        this.device = device;
    }
}
