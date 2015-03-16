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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="T_ALERT_FIELDS", joinColumns=@JoinColumn(name="alert_id"))
    private Map<String, String> fields;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public Alert() {
    }

    public Alert(Device device) {
        this.device = device;
    }
}
