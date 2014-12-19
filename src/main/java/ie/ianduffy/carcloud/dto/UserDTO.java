package ie.ianduffy.carcloud.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends AbstractAuditingEntityDTO {

    @Email
    @Size(min = 0, max = 100)
    private String email;

    @Size(min = 1, max = 50)
    private String firstName;

    @Size(min = 1, max = 50)
    private String lastName;

    @Size(min = 1, max = 100)
    private String password;

    @Pattern(regexp = "^\\+?[0-9. ()-]{10,25}$")
    private String phone;

    @Size(min = 0, max = 50)
    private String username;
}
