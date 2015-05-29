package ie.ianduffy.carcloud.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel
@ToString(exclude = {"password"})
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends AbstractAuditingEntityDTO<String> {

    @Email
    @ApiModelProperty
    @Size(min = 1, max = 100)
    private String email;

    @ApiModelProperty
    @Size(min = 1, max = 100)
    private String firstName;

    @ApiModelProperty
    @Size(min = 1, max = 100)
    private String lastName;

    @ApiModelProperty
    @Size(min = 1, max = 100)
    private String password;

    @ApiModelProperty
    @Pattern(regexp = "^\\+?[0-9. ()-]{10,25}$")
    private String phone;

    @ApiModelProperty
    @Size(min = 1, max = 100)
    private String username;

    @Override
    @JsonIgnore
    public String getId() {
        return username;
    }

    @Override
    @JsonIgnore
    public void setId(String id) {
        this.username = id;
    }
}
