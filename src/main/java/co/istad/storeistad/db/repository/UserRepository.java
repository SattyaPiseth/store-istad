package co.istad.storeistad.db.repository;

import co.istad.storeistad.db.entity.UserEntity;
import co.istad.storeistad.model.projection.user.UserEntityInfo;
import co.istad.storeistad.model.projection.user.UserProfileByUuid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u join fetch u.roleEntity role left join fetch role.permissionEntities where u.username = :username")
    UserEntity findByUsernameFetchRolePermission(@Param("username") String username);

    @Query("select u from UserEntity u join fetch u.roleEntity role left join fetch role.permissionEntities where u.uuid = :uuid ")
    UserProfileByUuid findByUuidFetchRolePermission(String uuid);

    @Query("""
            select (count(u) > 0) from UserEntity u
            where u.username = :username or u.email = :email and u.isDeleted = false""")
    boolean existsByUsernameOrEmailAndIsDeletedFalse(@Param("username") String username, @Param("email") String email);

    @Query("select u from UserEntity u where u.username = :username and (u.isDeleted = false or u.isDeleted is null) and u.status = true")
    Optional<UserEntity> findByUsernameAndIsDeletedFalseAndStatusTrue(@Param("username") String username);

    @Query("select u from UserEntity u where u.uuid = :uuid and u.status = true and u.isDeleted = false")
    Optional<UserEntity> findByUuidAndStatusTrueAndIsDeletedFalse(@Param("uuid") String uuid);

    @Query("""
            select (count(u) > 0) from UserEntity u
            where u.email = :email or u.username = :username and u.isDeleted = false""")
    boolean existsByEmailOrUsernameAndIsDeletedFalse(@Param("email") String email, @Param("username") String username);


    @Query("select (count(u) > 0) from UserEntity u where u.uuid = :uuid")
    boolean existsByUuid(@Param("uuid") String uuid);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.isDeleted = ?1, u.updatedAt = ?2, u.deletedAt = null, u.status = true where u.uuid = ?3")
    void updateIsDeletedAndUpdatedAtByUuid(Boolean isDeleted, Instant updatedAt, String uuid);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.isDeleted = ?1, u.deletedAt = ?2, u.status = false where u.uuid = ?3 and u.status = true")
    void updateIsDeletedAndDeletedAtByUuidAndStatusTrue(Boolean isDeleted, Instant deletedAt, String uuid);

    @Query("""
            select u from UserEntity u
            where(:query = 'ALL' or lower(u.uuid) like concat('%',lower(:query),'%') or lower(u.username) like concat('%',lower(:query),'%') or\s
            lower(u.email) like concat('%',lower(:query),'%') or lower(u.address) like concat('%', lower(:query),'%') or lower(u.roleEntity.name) like concat('%', lower(:query), '%') )""")
    Page<UserEntityInfo> findByQuery(@Param("query") String query, Pageable pageable);

}