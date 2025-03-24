package cn.abner.funchat.service;

import cn.abner.funchat.common.Paged;
import cn.abner.funchat.entity.UserBeauty;
import cn.abner.funchat.entity.param.UserBeautyParam;

import java.util.List;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
public interface ImUserBeautyService {
    List<UserBeauty> listByCondition(UserBeautyParam condition);

    Long countByCondition(UserBeautyParam condition);

    Paged<UserBeauty> pageByCondition(UserBeautyParam condition, Integer pageNum, Integer pageSize);

    UserBeauty findById(Long id);

    UserBeauty findByUsername(String username);

    UserBeauty findByUid(String uid);

    UserBeauty add(UserBeauty userBeauty);

    Integer addBatch(List<UserBeauty> userBeauties);

    UserBeauty update(UserBeauty userBeauty);

    void deleteById(Long id);

    void deleteByUid(String uid);
}
