package co.istad.storeistad.model.request.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Sattya
 * create at 2/5/2024 10:28 PM
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRQ {
    private String name;
    private String code;
}
