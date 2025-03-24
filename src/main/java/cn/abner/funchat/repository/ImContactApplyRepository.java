package cn.abner.funchat.repository;

import cn.abner.funchat.entity.ImContactApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/24
 */
@Repository
public interface ImContactApplyRepository extends JpaRepository<ImContactApply,Integer> {
}
