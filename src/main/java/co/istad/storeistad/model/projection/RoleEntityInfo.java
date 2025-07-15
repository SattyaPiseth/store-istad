package co.istad.storeistad.model.projection;

import java.time.Instant;

/**
 * Projection for {@link co.istad.storeistad.db.entity.RoleEntity}
 */
public interface RoleEntityInfo {
    Instant getCreatedAt();

    Instant getUpdatedAt();

    Long getId();

    String getName();

    String getCode();

    UserEntityInfo getUpdatedBy();

    UserEntityInfo getCreatedBy();

    /**
     * Projection for {@link co.istad.storeistad.db.entity.UserEntity}
     */
    interface UserEntityInfo {
        Long getId();

        String getUuid();

        String getUsername();

        String getEmail();

        String getBio();

        String getAvatar();

        String getAddress();

        String getPhone();
    }
}