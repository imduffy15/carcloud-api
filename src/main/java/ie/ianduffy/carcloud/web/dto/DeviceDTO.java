package ie.ianduffy.carcloud.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = false)
@Data
public class DeviceDTO extends AbstractAuditingEntityDTO {
    @Size(min = 1, max = 150)
    private String description;
    private Long id;
}
