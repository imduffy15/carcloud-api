package ie.ianduffy.carcloud.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "T_FIELD_STRING")
@EqualsAndHashCode(callSuper = true)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldString extends Field<String> {

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "value", length = 100, nullable = false)
    private String value;

    public FieldString() {
        super();
    }

    public FieldString(String name, String value) {
        super(name, value);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
