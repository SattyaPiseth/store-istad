package co.istad.storeistad.model.request.auth;


import co.istad.storeistad.exception.anotation.FieldsValueMatch;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * @author Sattya
 * create at 2/4/2024 8:36 PM
 */
@Getter
@FieldsValueMatch(field = "newPassword",fieldMatch = "confirmPassword")
public class ResetPasswordRQ {
    @NotBlank
    String newPassword;

    @NotBlank
    String confirmPassword;
}
