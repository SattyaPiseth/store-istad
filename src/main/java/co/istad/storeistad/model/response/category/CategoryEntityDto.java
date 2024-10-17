package co.istad.storeistad.model.response.category;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link co.istad.storeistad.model.response.category.CategoryEntityDto CategoryEntityDto}
 */
public record CategoryEntityDto(Long id, String uuid, String name, String description, Long parentId, String parentUuid,
                                String parentName, String parentDescription, Instant createdAt, Instant updatedAt,
                                String images) implements Serializable {
}