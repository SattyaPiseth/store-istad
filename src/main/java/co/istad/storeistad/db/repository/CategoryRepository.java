package co.istad.storeistad.db.repository;

import co.istad.storeistad.db.entity.CategoryEntity;
import co.istad.storeistad.model.projection.category.CategoryEntityInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Sattya
 * create at 1/27/2024 3:50 PM
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    @Query("""
            select c from CategoryEntity c
            where (:query = 'ALL' or lower(c.uuid) like concat('%',lower(:query),'%') or lower(c.name) like concat('%',lower(:query),'%') or lower(c.description) like concat('%', lower(:query),'%')) and c.deletedAt is null""")
    Page<CategoryEntityInfo> findByQuery(@Param("query") String query, Pageable pageable);

    @Query("select (count(c) > 0) from CategoryEntity c where c.name = :name and c.deletedAt is null")
    boolean existsByName(@Param("name") String name);

    @Query("select c from CategoryEntity c where c.uuid = :uuid ")
    CategoryEntity findByUuid(@Param("uuid") String uuid);

    @Query("select (count(c) > 0) from CategoryEntity c where c.uuid = :uuid")
    boolean existsByUuid(@Param("uuid") String uuid);

    @Query("select (count(c) > 0) from CategoryEntity c where c.uuid = :uuid and c.deletedAt is null")
    boolean existsByUuidAndDeletedAtIsNull(@Param("uuid") String uuid);


}
