package ie.ianduffy.carcloud.domain;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "T_FIELD_BOOLEAN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldBoolean extends Field<Boolean> {


    private Boolean value;

    public FieldBoolean() {

    }

    public FieldBoolean(String name, Boolean value) {
        super(name);
        this.value = value;
    }
}
