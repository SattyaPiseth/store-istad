package co.istad.storeistad.db.repository;

import co.istad.storeistad.db.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Sattya
 * create at 2/1/2024 12:02 AM
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    @Query("select p from PasswordResetToken p where p.token = :token")
    Optional<PasswordResetToken> findByToken(@Param("token") String token);

    @Transactional
    @Modifying
    @Query("delete from PasswordResetToken p where p.token = :token")
    void deleteByToken(@Param("token") String token);

    @Query("select (count(p) > 0) from PasswordResetToken p where p.user.email = :email")
    boolean existsByUserEmail(@Param("email") String email);
}
