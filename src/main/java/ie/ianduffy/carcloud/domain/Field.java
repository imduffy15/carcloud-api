package ie.ianduffy.carcloud.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A Field.
 */
@Data
@Entity
@Table(name = "T_FIELD")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FieldString.class, name = "string"),
    @JsonSubTypes.Type(value = FieldBoolean.class, name = "boolean"),
    @JsonSubTypes.Type(value = FieldInteger.class, name = "integer"),
})
public abstract class Field<T extends Comparable> implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    public Field() {
    }

    public Field(String name, T value) {
        this.name = name;
        this.setValue(value);
    }

    @Override
    public int compareTo(Object obj) {
        Field that = (Field) obj;
        return this.getValue().compareTo(that.getValue());
    }

    public abstract T getValue();

    public abstract void setValue(T value);

}
