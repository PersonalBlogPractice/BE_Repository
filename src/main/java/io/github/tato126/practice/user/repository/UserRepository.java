package io.github.tato126.practice.user.repository;

import io.github.tato126.practice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 사용자 데이터 접근을 위한 Repository 인터페이스입니다.
 * <p>
 * Spring Data JPA를 사용하여 User 엔티티의 CRUD 작업을 처리합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일로 사용자를 조회합니다.
     *
     * @param email 조회할 사용자 이메일
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByEmail(String email);
}
