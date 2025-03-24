package cn.abner.funchat.repository;

import cn.abner.funchat.entity.UserBeauty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
@Repository
public interface ImUserBeautyRepository extends JpaRepository<UserBeauty, Long> {
    Optional<UserBeauty> findByUsername(String username);

    Optional<UserBeauty> findByUid(String uid);

    void deleteByUid(String uid);
}
