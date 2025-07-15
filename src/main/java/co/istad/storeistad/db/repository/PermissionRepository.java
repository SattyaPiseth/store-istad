package co.istad.storeistad.db.repository;

import co.istad.storeistad.db.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Sattya
 * create at 2/6/2024 12:28 PM
 */
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    @Query("select p from PermissionEntity p where p.id in :ids")
    List<PermissionEntity> findByIdIn(@Param("ids") Collection<Long> ids);

}
