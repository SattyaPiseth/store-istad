package co.istad.storeistad.model.request.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * @author Sombath
 * create at 24/1/24 4:02 PM
 */

@Data
@Builder
public class RoleRQ {
    @NotBlank
    private String name;
    @NotBlank
    private String code;

}
