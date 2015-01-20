package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_FIELD_STRING")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldString extends Field<String> {

    private String value;

    public FieldString() {
    }

    public FieldString(String name, String value) {
        super(name);
        this.value = value;
    }
}
