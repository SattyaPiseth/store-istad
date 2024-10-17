package co.istad.storeistad.model.request.auth;


import co.istad.storeistad.exception.anotation.FieldsValueMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sattya
 * create at 2/8/2024 5:21 PM
 */
@Getter
@Setter
@Builder
@FieldsValueMatch(field = "password",fieldMatch = "confirmPassword")
public class RegisterRQ {
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
}
