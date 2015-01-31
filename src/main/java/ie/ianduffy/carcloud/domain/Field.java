package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * A Field.
 */
@Data
@Entity
@Table(name = "T_FIELD")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Field<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    public Field() {}
    public Field(String name, T value) {
        this.name = name;
        this.setValue(value);
    }

    public abstract T getValue();
    public abstract void setValue(T value);

}
