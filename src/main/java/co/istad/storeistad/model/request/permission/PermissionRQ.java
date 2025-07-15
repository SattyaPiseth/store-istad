package co.istad.storeistad.model.request.permission;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Sattya
 * create at 2/6/2024 3:54 PM
 */
@Data
public class PermissionRQ {
    @NotNull
    private Long permissionId;
    @NotNull
    private Boolean status;

}

