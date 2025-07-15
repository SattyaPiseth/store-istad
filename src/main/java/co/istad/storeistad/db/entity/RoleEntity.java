package co.istad.storeistad.db.entity;

import co.istad.storeistad.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Sombath
 * create at 12/9/23 1:49 AM
 */

@Entity
@Table(name = "role", indexes = {
        @Index(name = "idx_role_name", columnList = "name", unique = true),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@EntityListeners(AuditingEntityListener.class)
public class RoleEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;
    @JsonIgnore
    @OneToMany(mappedBy = "roleEntity", fetch = FetchType.LAZY)
    private Set<UserEntity> userEntities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_has_permissions",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "id"))
    @JsonProperty("permissions")
    private List<PermissionEntity> permissionEntities = new ArrayList<>();

}
