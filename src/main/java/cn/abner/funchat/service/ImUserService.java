package cn.abner.funchat.service;

import cn.abner.funchat.common.Paged;
import cn.abner.funchat.entity.ImUser;
import cn.abner.funchat.entity.param.UserParam;
import cn.abner.funchat.entity.param.UserRegister;

import java.util.List;
import java.util.Map;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
public interface ImUserService {

    List<ImUser> listByCondition(UserParam condition);

    Long countByCondition(UserParam condition);

    Paged<ImUser> pageByCondition(UserParam condition, Integer pageNum, Integer pageSize);

    ImUser findById(String uid);

    ImUser findByUsername(String username);

    ImUser register(UserRegister register);

    Map<String, Object> login(String username, String password);

    ImUser add(ImUser user);

    Integer addBatch(List<ImUser> users);

    ImUser update(ImUser user);

    void deleteById(String uid);

    void deleteByUsername(String username);

    ImUser getCurrentUser();

}
