package co.istad.storeistad.model.projection.user;

import java.time.Instant;
import java.util.List;

/**
 * Projection for {@link co.istad.storeistad.db.entity.UserEntity}
 */
public interface UserProfileByUuid {
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

    RoleEntityInfo getRoleEntity();

    /**
     * Projection for {@link co.istad.storeistad.db.entity.RoleEntity}
     */
    interface RoleEntityInfo {
        Instant getCreatedAt();

        Instant getUpdatedAt();

        Long getId();

        String getName();

        String getCode();

        roleEntityInfoChild getUpdatedBy();

        interface RoleEntityInfoChild {
            Long getId();

            String getUsername();
        }
        List<PermissionEntityInfo> getPermissionEntities();

        /**
         * Projection for {@link co.istad.storeistad.db.entity.PermissionEntity}
         */
        interface PermissionEntityInfo {
            Long getId();

            String getName();

            String getModule();
        }

        interface roleEntityInfoChild {
            Long getId();

            String getUsername();
        }
    }
}