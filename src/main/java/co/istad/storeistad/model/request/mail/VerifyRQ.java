package co.istad.storeistad.model.request.mail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Sattya
 * create at 1/30/2024 12:33 PM
 */
@Getter
@AllArgsConstructor
public class VerifyRQ {
    @NotBlank
    @Email
    String email;
    @NotBlank
    String verifiedCode;
}
