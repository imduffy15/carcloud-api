package ie.ianduffy.carcloud.web.munic.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;

@Data
public class FieldDTO {
    private String name;

    @JsonUnwrapped
    private FieldDTOValue value;

    public FieldDTO(String name, FieldDTOValue value) {
        this.name = name;
        this.value = value;
    }
}
