package ie.ianduffy.carcloud.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A Device.
 */
@Data
@Entity
@NamedQueries({
    @NamedQuery(name="Device.findAllForUser", query="select d from Device d where ?1 MEMBER OF d.owners"),
    @NamedQuery(name="Device.findOneForUser", query="select d from Device d where ?1 MEMBER OF d.owners and d.id = ?2")
})
@Table(name = "T_DEVICE")
@ToString(exclude = {"owners", "tracks"})
@EqualsAndHashCode(exclude = {"owners", "tracks"}, callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Device extends AbstractAuditingEntity<Long> implements Serializable {

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
    private List<User> owners = new ArrayList<>();

    @OrderBy
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Track> tracks = new ArrayList<>();

    public Device() {
    }

}
