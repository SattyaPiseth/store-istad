package co.istad.storeistad.model.projection.product;

import java.time.Instant;

/**
 * Projection for {@link co.istad.storeistad.db.entity.ProductEntity}
 */
public interface ProductEntityInfo {
    Long getId();

    String getUuid();

    String getName();

    String getDescription();

    String getImage();

    CategoryEntityInfo getCategory();
    
    Instant getCreatedAt();
    Instant getUpdatedAt();

    /**
     * Projection for {@link co.istad.storeistad.db.entity.CategoryEntity}
     */
    interface CategoryEntityInfo {
        Long getId();

        String getUuid();

        String getName();

//        String getDescription();

        CategoryEntityInfo1 getParent();

        /**
         * Projection for {@link  co.istad.storeistad.db.entity.CategoryEntity}
         */
        interface CategoryEntityInfo1 {
            Long getId();

            String getUuid();

            String getName();

//            String getDescription();
        }
    }
}