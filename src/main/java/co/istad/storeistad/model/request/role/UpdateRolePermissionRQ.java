package co.istad.storeistad.model.request.role;


import co.istad.storeistad.model.request.permission.PermissionRQ;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author Sattya
 * create at 2/6/2024 3:51 PM
 */
@Data
public class UpdateRolePermissionRQ {
    @NotNull
    private Long roleId;
    @NotEmpty
    private List<@Valid PermissionRQ> permissions;

}
