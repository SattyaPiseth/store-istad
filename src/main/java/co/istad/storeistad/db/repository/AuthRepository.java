package co.istad.storeistad.db.repository;


import co.istad.storeistad.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Sattya
 * create at 1/29/2024 11:10 PM
 */
public interface AuthRepository extends JpaRepository<UserEntity,Long> {
    @Transactional
    @Modifying
    @Query("update UserEntity u set u.verifiedCode = ?1 where u.email = ?2 and u.isDeleted = false and u.status = false ")
    void updateVerifiedCodeByEmailAndIsDeletedFalseAndStatusFalse(String verifiedCode, String email);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.verifiedToken = ?1 where u.username = ?2")
    void updateVerifiedTokenAndDeletedAtByUsername(String verifiedToken, String username);


    @Query("select u from UserEntity u where u.email = :email and u.verifiedCode = :verifiedCode and u.isDeleted = false")
    Optional<UserEntity> findByUsernameAndVerifiedCodeAndIsDeletedFalse(@Param("email") String email, @Param("verifiedCode") String verifiedCode);


    @Query("select u from UserEntity u where u.verifiedToken = :verifiedToken and u.isDeleted = false")
    Optional<UserEntity> findByVerifiedTokenAndIsDeletedFalse(@Param("verifiedToken") String verifiedToken);

    @Query("select u from UserEntity u where u.email = :email and u.isDeleted = false and u.status = true")
    Optional<UserEntity> findByEmailAndIsDeletedFalseAndStatusTrue(@Param("email") String email);


    @Query("select u from UserEntity u where u.email = :email and u.isDeleted = false and u.status = false")
    Optional<UserEntity> findByEmailAndIsDeletedFalseAndStatusFalse(@Param("email") String email);

    boolean existsByEmailAndIsDeletedFalseAndStatusFalse(String email);

}
