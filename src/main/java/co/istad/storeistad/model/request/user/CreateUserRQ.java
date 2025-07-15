package co.istad.storeistad.model.request.user;


import co.istad.storeistad.exception.anotation.FieldsValueMatch;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Sattya
 * create at 1/29/2024 2:58 PM
 */
@Getter
@Setter
@Builder
@FieldsValueMatch(field = "password",fieldMatch = "confirmPassword")
public class CreateUserRQ {
    @NotBlank
    String username;
    @NotBlank
    String password;

    @NotBlank
    String confirmPassword;

    @NotBlank
    @Email
    String email;
    @NotBlank
    @Size(max = 100)
    String name;
    @Size(max = 200)
    @NotBlank
    String bio;

    String avatar;
    @NotBlank
    @Size(max = 200)
    String address;
    @NotBlank
    @Size(min = 8, max = 50)
    String phone;

    @Size(max = 1)
    @NotNull(message =  "roleIds is required")
    Set<@Positive Long> roleIds;
}
