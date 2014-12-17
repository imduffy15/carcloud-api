package ie.ianduffy.carcloud.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
public class TrackDTO extends AbstractAuditingEntityDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Double latitude;
    private Double longitude;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime receivedAt;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime recordedAt;

}
