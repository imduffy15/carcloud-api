package ie.ianduffy.carcloud.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DeviceDTO extends AbstractAuditingEntityDTO {
    @Size(min = 1, max = 150)
    private String description;
    private Long id;
}
