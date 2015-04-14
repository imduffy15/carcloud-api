package ie.ianduffy.carcloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "T_USER")
@NamedQueries({
    @NamedQuery(name = "User.findOneByUsernameOrEmailOrPhone", query = "select u from User u where u.username = ?1 or u.email = ?1 or u.phone = ?1"),
})
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
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @Size(min = 1, max = 100)
    @Column(name = "first_name", length = 100)
    private String firstName;

    @Size(min = 1, max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;

    @NotNull
    @JsonIgnore
    @Size(min = 5, max = 100)
    @Column(length = 100)
    private String password;

    @NotNull
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^\\+?[0-9. ()-]{10,25}$")
    @Column(length = 100, unique = true)
    private String phone;

    @Id
    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false, updatable = false)
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
