package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "T_FIELD_BOOLEAN")
@EqualsAndHashCode(callSuper = true)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldBoolean extends Field<Boolean> {

    private Boolean value;

    public FieldBoolean() {
        super();
    }

    public FieldBoolean(String name, Boolean value) {
        super(name, value);
    }

    @Override
    public int compareTo(Object obj) {
        return 0;
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
