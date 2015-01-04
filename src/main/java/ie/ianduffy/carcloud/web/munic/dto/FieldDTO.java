package ie.ianduffy.carcloud.web.munic.dto;

import lombok.Data;

@Data
public class FieldDTO {
    private String name;
    private String value;

    public FieldDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
