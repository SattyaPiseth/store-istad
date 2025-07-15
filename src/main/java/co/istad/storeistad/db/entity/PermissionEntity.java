package co.istad.storeistad.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * @author Sombath
 * create at 12/9/23 1:49 AM
 */

@Entity
@Table(name = "permission", indexes = {
        @Index(name = "idx_permission_name", columnList = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "module", nullable = false)
    private String module;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissionEntities")
    private Set<RoleEntity> roles;

}
