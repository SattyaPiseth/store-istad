package co.istad.storeistad.db.repository;

import co.istad.storeistad.db.entity.ProductEntity;
import co.istad.storeistad.model.projection.product.ProductEntityInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Sattya
 * create at 1/27/2024 3:52 PM
 */
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    @Query("""
            select p from ProductEntity p
            where (:query = 'ALL' or lower(p.uuid) like concat('%',lower(:query),'%') or lower(p.name) like concat('%',lower(:query),'%') or lower(p.category.name) like concat('%',lower(:query),'%')) and p.deletedAt is null""")
    Page<ProductEntityInfo> findByQuery(@Param("query") String query, Pageable pageable);

    @Query("select (count(p) > 0) from ProductEntity p where p.uuid = :uuid and p.deletedAt is null")
    boolean existsByUuidAndDeletedAtIsNull(@Param("uuid") String uuid);

    @Query("select p from ProductEntity p where p.uuid = :uuid")
    ProductEntity findByUuid(@Param("uuid") String uuid);

    @Query("select (count(p) > 0) from ProductEntity p where p.name = :name and p.deletedAt is null")
    boolean existsByName(@Param("name") String name);

    @Query("select (count(p) > 0) from ProductEntity p where p.uuid = :uuid")
    boolean existsByUuid(@Param("uuid") String uuid);
}
