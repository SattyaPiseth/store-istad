package co.istad.storeistad.model.projection.user;

import java.time.Instant;

/**
 * Projection for {@link co.istad.storeistad.db.entity.UserEntity}
 */
public interface UserEntityInfo {
    Long getId();

    String getUuid();

    String getUsername();

    String getEmail();

    String getName();

    String getBio();

    String getAvatar();

    String getAddress();

    String getPhone();

    Boolean getStatus();

    Instant getCreatedAt();
    Instant getDeletedAt();
    Instant getUpdatedAt();
    UserEntityInfoChild getCreatedBy();
    UserEntityInfoChild getUpdatedBy();

    RoleEntityInfo getRoleEntity();

    /**
     * Projection for {@link co.istad.storeistad.db.entity.RoleEntity}
     */
    interface RoleEntityInfo {
        Long getId();

        String getName();

        String getCode();
    }

    /**
     * Projection for {@link co.istad.storeistad.db.entity.UserEntity}
     */
    interface UserEntityInfoChild {
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