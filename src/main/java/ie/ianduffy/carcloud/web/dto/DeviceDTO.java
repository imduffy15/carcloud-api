package ie.ianduffy.carcloud.web.dto;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceDTO extends AbstractAuditingEntityDTO<Long> {

    @Size(min = 1, max = 150)
    private String description;

    private Long id;
}
