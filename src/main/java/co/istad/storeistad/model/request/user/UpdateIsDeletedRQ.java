package co.istad.storeistad.model.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Sattya
 * create at 2/5/2024 1:37 AM
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIsDeletedRQ {
    @NotNull
    private Boolean isDeleted;
}
