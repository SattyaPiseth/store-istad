package co.istad.storeistad.model.request.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sattya
 * create at 2/9/2024 5:36 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRQ {

    private String name;

    private String description;

    private Long parentId;
}
