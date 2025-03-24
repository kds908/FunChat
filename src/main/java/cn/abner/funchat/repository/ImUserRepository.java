package cn.abner.funchat.repository;

import cn.abner.funchat.entity.ImUser;
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
public interface ImUserRepository extends JpaRepository<ImUser, String> {
    Optional<ImUser> findByUsername(String username);

    void deleteByUsername(String username);
}
