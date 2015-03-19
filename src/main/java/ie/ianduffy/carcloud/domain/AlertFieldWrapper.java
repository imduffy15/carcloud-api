package ie.ianduffy.carcloud.domain;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_ALERT_FIELD_WRAPPER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlertFieldWrapper {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Field field;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Operation operation;
}
