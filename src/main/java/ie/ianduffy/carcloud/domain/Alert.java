package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "T_ALERT")
@ToString(exclude = {"device"})
@NamedQueries({
    @NamedQuery(name = "Alert.findAllForUser", query = "select a from Alert a where ?1 MEMBER OF a.device.owners"),
    @NamedQuery(name = "Alert.findOneForUser", query = "select a from Alert a where a.id = ?2 and ?1 MEMBER OF a.device.owners"),
})
@EqualsAndHashCode(exclude = {"device"}, callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Alert extends AbstractAuditingEntity<Long> implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public Alert() {
    }

    public Alert(Device device) {
        this.device = device;
    }
}
