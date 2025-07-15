package co.istad.storeistad.model.response.productItems;

import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO for {@link com.example.demo.db.entity.ProductItemEntity}
 */
public record ProductItemEntityDto(Long id, String code, Integer quantity, String image, BigDecimal price,
                                   ProductEntityDto product,
                                   Set<VariationOptionEntityDto> variationOptions) implements Serializable {
    /**
     * DTO for {@link com.example.demo.db.entity.ProductEntity}
     */
    public record ProductEntityDto(Long id, String uuid, @Size(max = 150) String name, String description, String image,
                                   CategoryEntityDto category) implements Serializable {
        /**
         * DTO for {@link com.example.demo.db.entity.CategoryEntity}
         */
        public record CategoryEntityDto(Long id, String uuid, String name, String description) implements Serializable {
        }
    }

    /**
     * DTO for {@link com.example.demo.db.entity.VariationOptionEntity}
     */
    public record VariationOptionEntityDto(Long id, String value,
                                           VariationEntityDto variation) implements Serializable {
        /**
         * DTO for {@link com.example.demo.db.entity.VariationEntity}
         */
        public record VariationEntityDto(Long id, String name) implements Serializable {
        }
    }
}