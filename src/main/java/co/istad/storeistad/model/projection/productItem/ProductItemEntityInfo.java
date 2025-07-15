package co.istad.storeistad.model.projection.productItem;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

/**
 * Projection for {@link co.istad.storeistad.db.entity.ProductItemEntity}
 */
public interface ProductItemEntityInfo {
    Long getId();

    String getCode();

    Integer getQuantity();

    String getImage();

    BigDecimal getPrice();

    ProductEntityInfo getProduct();

    Instant getCreatedAt();
    Instant getUpdatedAt();

    Set<VariationOptionEntityInfo> getVariationOptions();

    /**
     * Projection for {@link co.istad.storeistad.db.entity.ProductEntity}
     */
    interface ProductEntityInfo {
        Long getId();

        String getUuid();

        String getName();

//        String getDescription();

//        String getImage();

        CategoryEntityInfo getCategory();

        /**
         * Projection for {@link co.istad.storeistad.db.entity.CategoryEntity}
         */
        interface CategoryEntityInfo {
            Long getId();

            String getUuid();

            String getName();

//            String getDescription();
        }
    }

    /**
     * Projection for {@link co.istad.storeistad.db.entity.VariationOptionEntity}
     */
    interface VariationOptionEntityInfo {
        Long getId();

        String getValue();

        VariationEntityInfo getVariation();

        /**
         * Projection for {@link co.istad.storeistad.db.entity.VariationEntity}
         */
        interface VariationEntityInfo {
            Long getId();

            String getName();
        }
    }
}