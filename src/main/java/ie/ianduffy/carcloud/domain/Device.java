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
 * <p/>
 * SPAM 0.0.1 - Extention of a base class.
 */
@Data
@Entity
@Table(name = "T_DEVICE")
@ToString(exclude = {"owners", "tracks"})
@EqualsAndHashCode(exclude = {"owners", "tracks"})
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
    @JoinTable(
        name = "T_DEVICE_OWNER",
        joinColumns = {@JoinColumn(name = "device_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "login", referencedColumnName = "login")})
    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<User> owners = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device", fetch = FetchType.LAZY)
    private Set<Track> tracks = new HashSet<>();

    public Device() {
    }

}
