package co.istad.storeistad.model.request.user;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Sattya
 * create at 2/4/2024 10:18 PM
 */
@Getter
@AllArgsConstructor
public class UpdateProfileRQ {

    private String username;
    @Email
    private String email;

    private String name;

    private String bio;

    private String avatar;

    private String address;

    private String phone;
}
