package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Device.
 */
@Data
@Entity
@NamedQueries({
    @NamedQuery(name = "Device.findAllForUser", query = "select d from Device d where ?1 MEMBER OF d.owners"),
    @NamedQuery(name = "Device.findOneForUser", query = "select d from Device d where ?1 MEMBER OF d.owners and d.id = ?2")
})
@Table(name = "T_DEVICE")
@ToString(exclude = {"owners", "tracks", "alerts"})
@EqualsAndHashCode(exclude = {"owners", "tracks", "alerts"}, callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Device extends AbstractAuditingEntity<Long> implements Serializable {

    @LazyCollection(LazyCollectionOption.EXTRA)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    private List<Alert> alerts = new ArrayList<>();

    @Size(min = 1, max = 150)
    @Column(name = "description", length = 150)
    private String description;

    @Id
    @NotNull
    @Column(unique = true)
    private Long id;

    @OrderBy
    @JoinTable(
        name = "T_DEVICE_OWNER",
        joinColumns = {@JoinColumn(name = "device_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "username", referencedColumnName = "username")})
    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<User> owners = new HashSet<>();

    @LazyCollection(LazyCollectionOption.EXTRA)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    private List<Track> tracks = new ArrayList<>();

    public Device() {
    }
}
