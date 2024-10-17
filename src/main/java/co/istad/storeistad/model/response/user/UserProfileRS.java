package co.istad.storeistad.model.response.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sattya
 * create at 1/31/2024 8:45 AM
 */
@Getter
@Setter
public class UserProfileRS {
    String uuid;
    String username;
    String email;
    String bio;
    String avatar;
    String address;
    String phone;
    String roleName;
    String roleCode;

}
