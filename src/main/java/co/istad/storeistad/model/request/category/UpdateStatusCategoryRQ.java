package co.istad.storeistad.model.request.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Sattya
 * create at 2/9/2024 5:30 PM
 */
@Data
public class UpdateStatusCategoryRQ {
//    @NotBlank(message = "uuid is required")
//    private String uuid;

    @NotNull(message = "status is required")
    private Boolean status;
}
