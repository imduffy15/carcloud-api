package ie.ianduffy.carcloud.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeviceDTO extends AbstractAuditingEntityDTO {
    @Size(min = 1, max = 150)
    private String description;

    @NotNull
    private Long id;

    public DeviceDTO() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass() + "{");
        sb.append(", id='").append(id).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
