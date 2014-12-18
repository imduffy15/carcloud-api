package ie.ianduffy.carcloud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceDTO extends AbstractAuditingEntityDTO {
    @Size(min = 1, max = 150)
    private String description;
    private Long id;
}
