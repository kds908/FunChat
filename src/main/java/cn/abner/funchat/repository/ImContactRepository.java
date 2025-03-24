package cn.abner.funchat.repository;

import cn.abner.funchat.entity.ContactKey;
import cn.abner.funchat.entity.ImContact;
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
public interface ImContactRepository extends JpaRepository<ImContact, ContactKey> {

}
