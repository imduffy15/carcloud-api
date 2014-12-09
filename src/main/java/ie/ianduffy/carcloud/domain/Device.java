package ie.ianduffy.carcloud.domain;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A Device.
 * <p/>
 * SPAM 0.0.1 - Extention of a base class.
 */
@Data
@Entity
@Table(name = "T_DEVICE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Device extends AbstractAuditingEntity implements Serializable {

    @Size(min = 1, max = 150)
    @Column(name = "description", length = 150)
    private String description;

    @Id
    @NotNull
    @Column(unique = true)
    private Long id;

    @OrderBy
    @Fetch(FetchMode.JOIN)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "T_DEVICE_OWNER",
        joinColumns = {@JoinColumn(name = "device_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "login", referencedColumnName = "login")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<User> owners = new ArrayList<>();
}
