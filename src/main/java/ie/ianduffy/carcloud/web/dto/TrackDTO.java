package ie.ianduffy.carcloud.web.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import ie.ianduffy.carcloud.domain.Field;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import java.util.List;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
public class TrackDTO extends AbstractAuditingEntityDTO<Long> {

    @ApiModelProperty
    private Long id;

    @ApiModelProperty
    private Double latitude;

    @ApiModelProperty
    private Double longitude;

    @ApiModelProperty
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime receivedAt;

    @ApiModelProperty
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime recordedAt;

    @ApiModelProperty
    private List<Field> fields;

    public TrackDTO() {

    }

    public TrackDTO(Long id, Double latitude, Double longitude, DateTime receivedAt, DateTime recordedAt) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.receivedAt = receivedAt;
        this.recordedAt = recordedAt;
    }

}
