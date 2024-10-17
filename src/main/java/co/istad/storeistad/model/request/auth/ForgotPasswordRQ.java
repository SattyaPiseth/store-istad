package co.istad.storeistad.model.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * @author Sattya
 * create at 1/30/2024 11:10 PM
 */
@Getter
public class ForgotPasswordRQ {
    @NotBlank
    @Email
    String email;
}
