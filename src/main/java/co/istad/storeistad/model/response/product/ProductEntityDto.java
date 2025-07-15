package co.istad.storeistad.model.response.product;

import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link co.istad.storeistad.db.entity.ProductEntity ProductEntity}
 */
public record ProductEntityDto(Long id, String uuid, @Size(max = 150) String name, String description, String image,
                               CategoryEntityDto category, Instant createdAt, Instant updatedAt) implements Serializable {
    /**
     * DTO for {@link co.istad.storeistad.db.entity.CategoryEntity CategoryEntity}
     */
    public record CategoryEntityDto(Long id, String uuid, String name, String description,
                                    CategoryEntityDto1 parent) implements Serializable {
        /**
         * DTO for {@link co.istad.storeistad.db.entity.CategoryEntity CategoryEntity}
         */
        public record CategoryEntityDto1(Long id, String uuid, String name, String description) implements Serializable {
        }
    }
}