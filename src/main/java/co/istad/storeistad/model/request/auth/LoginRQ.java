package co.istad.storeistad.model.request.auth;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author Sattya
 * create at 11/9/23 1:48 PM
 */

@Data
public class LoginRQ {

    @NotEmpty(message = "Please provide a username, email, phone")
    private String username;

    @NotEmpty(message = "Please provide a password")
    private String password;
}
