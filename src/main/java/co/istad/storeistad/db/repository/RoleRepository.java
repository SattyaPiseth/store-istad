package co.istad.storeistad.db.repository;

import co.istad.storeistad.db.entity.RoleEntity;
import co.istad.storeistad.model.projection.RoleEntityInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("select r from RoleEntity r where (:query = 'ALL' or lower(r.name) like concat(lower(:query) , '%') or lower(r.code) like concat(lower(:query) , '%'))")
    Page<RoleEntityInfo> findByNameStartsWithAndCode(String query, Pageable pageable);

    @Query("select (count(r) > 0) from RoleEntity r where upper(r.name) = upper(:name) and upper(r.code) = upper(:code)")
    boolean existsByNameAndCodeAllIgnoreCase(@Param("name") String name, @Param("code") String code);

    @Query("select r from RoleEntity r left join fetch r.permissionEntities where r.id = ?1")
    RoleEntity findByIdFetchPermission(Long id);
}