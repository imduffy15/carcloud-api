package ie.ianduffy.carcloud.domain;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 */
@Data
@Entity
@Table(name = "T_AUTHORITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable, GrantedAuthority {

    @Id
    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false, updatable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
