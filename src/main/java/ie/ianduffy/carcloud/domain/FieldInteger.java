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
@Table(name = "T_FIELD_INTEGER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldInteger extends Field<Integer> {


    private Integer value;

    public FieldInteger() {

    }

    public FieldInteger(String name, Integer value) {
        super(name);
        this.value = value;
    }
}
