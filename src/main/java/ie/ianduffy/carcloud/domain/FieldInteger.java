package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "T_FIELD_INTEGER")
@EqualsAndHashCode(callSuper = true)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldInteger extends Field<Integer> {

    @NotNull
    @Column(name = "value", nullable = false)
    private Integer value;

    public FieldInteger() {
        super();
    }

    public FieldInteger(String name, Integer value) {
        super(name, value);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }
}
