package co.istad.storeistad.model.projection.category;

import java.time.Instant;

/**
 * Projection for {@link CategoryEntityInfo}
 */
public interface CategoryEntityInfo {
    Long getId();

    String getUuid();

    String getName();

    String getDescription();
    CategoryParent getParent();

    String getImages();

    Instant getCreatedAt();

    Instant getUpdatedAt();

    interface CategoryParent{
        Long getId();

        String getUuid();

        String getName();
    }

}