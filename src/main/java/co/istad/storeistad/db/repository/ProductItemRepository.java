package co.istad.storeistad.db.repository;


import co.istad.storeistad.db.entity.ProductItemEntity;
import co.istad.storeistad.model.projection.productItem.ProductItemEntityInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * @author Sattya
 * create at 2/10/2024 9:02 PM
 */
public interface ProductItemRepository extends JpaRepository<ProductItemEntity, Long>, JpaSpecificationExecutor<ProductItemEntity> {
    @Query("select p from ProductItemEntity p left join fetch p.variationOptions where p.id = ?1 and p.deletedAt is null")
    ProductItemEntity findByIdFetchVariationOptions(Long id);

    @Query("""
    select p from ProductItemEntity p left join p.variationOptions variationOptions
    where (:query = 'ALL' or lower(p.code) like concat('%',lower(:query),'%') 
        or lower(p.product.name) like concat('%',lower(:query),'%') 
        or lower(p.product.category.name) like concat('%',lower(:query),'%') 
        or lower(variationOptions.value) like concat('%',lower(:query),'%'))
        and p.deletedAt is null
""")
    Page<ProductItemEntityInfo> findByQuery(@Param("query") String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ProductItemEntity p set p.deletedAt = ?1 where p.id = ?2")
    void updateDeletedAtById(Instant deletedAt, Long id);
}