package co.istad.storeistad.model.request.auth;


import co.istad.storeistad.exception.anotation.FieldsValueMatch;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * @author Sattya
 * create at 1/31/2024 12:51 AM
 */
@Getter
@FieldsValueMatch(field = "newPassword",fieldMatch = "confirmPassword")
public class ChangePasswordRQ {

    @NotBlank
    String oldPassword;

    @NotBlank
    String newPassword;

    @NotBlank
    String confirmPassword;
}
