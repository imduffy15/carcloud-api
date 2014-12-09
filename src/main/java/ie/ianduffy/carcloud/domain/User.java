package ie.ianduffy.carcloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A user.
 * <p/>
 * SPAM 0.0.1 - Usage of a base class.
 */
@Data
@Entity
@Table(name = "T_USER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

    @OrderBy
    @ManyToMany
    @JoinTable(
        name = "T_USER_AUTHORITY",
        joinColumns = {@JoinColumn(name = "login", referencedColumnName = "login")},
        inverseJoinColumns = {@JoinColumn(name = "name", referencedColumnName = "name")})
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
    @NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50)
    private String login;
    @JsonIgnore
    @Size(min = 0, max = 100)
    @Column(length = 100)
    private String password;

    @NotNull
    @Pattern(regexp = "^\\+?[0-9. ()-]{10,25}$")
    @Column(length = 100, unique = true)
    private String phone;
}
