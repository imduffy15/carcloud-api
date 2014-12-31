package ie.ianduffy.carcloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A user.
 */
@Data
@Entity
@Table(name = "T_USER")
@ToString(exclude = {"authorities", "password"})
@EqualsAndHashCode(exclude = {"authorities"}, callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity<String> implements Serializable, UserDetails {

    @OrderBy
    @JoinTable(
        name = "T_USER_AUTHORITY",
        joinColumns = {@JoinColumn(name = "username", referencedColumnName = "username")},
        inverseJoinColumns = {@JoinColumn(name = "name", referencedColumnName = "name")})
    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Authority> authorities = new ArrayList<>();

    @Email
    @NotNull
    @Size(min = 0, max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @Size(min = 1, max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(min = 1, max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @JsonIgnore
    @Size(min = 0, max = 100)
    @Column(length = 100)
    private String password;

    @NotNull
    @Pattern(regexp = "^\\+?[0-9. ()-]{10,25}$")
    @Column(length = 100, unique = true)
    private String phone;

    @NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50, unique = true)
    private String username;

    @Override
    @JsonIgnore
    public String getId() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
