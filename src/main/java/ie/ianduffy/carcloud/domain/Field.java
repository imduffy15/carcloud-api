package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * A Field.
 */
@Data
@Entity
@Table(name = "T_FIELD")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
abstract public class Field<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    Field(){
    }

    protected Field(String name) {
        this.name = name;
    }

    abstract T getValue();
    abstract void setValue(T value);

}
