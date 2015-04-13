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
@Table(name = "T_FIELD_BOOLEAN")
@EqualsAndHashCode(callSuper = true)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldBoolean extends Field<Boolean> {

    @NotNull
    @Column(name = "value", nullable = false)
    private Boolean value;

    public FieldBoolean() {
        super();
    }

    public FieldBoolean(String name, Boolean value) {
        super(name, value);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
