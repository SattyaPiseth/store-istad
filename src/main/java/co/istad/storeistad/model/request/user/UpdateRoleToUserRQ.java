package co.istad.storeistad.model.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Sattya
 * create at 2/6/2024 12:06 AM
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleToUserRQ {

    @NotNull(message = "At least one role ID must be provided.")
    @Size(max = 1, message = "At least one role ID must be provided.")
    private Set<Long> roleId;
}
