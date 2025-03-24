package cn.abner.funchat.service.impl;

import cn.abner.funchat.common.Paged;
import cn.abner.funchat.entity.UserBeauty;
import cn.abner.funchat.entity.param.UserBeautyParam;
import cn.abner.funchat.repository.ImUserBeautyRepository;
import cn.abner.funchat.service.ImUserBeautyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
@Service
public class ImUserBeautyServiceImpl implements ImUserBeautyService {
    @Autowired
    private ImUserBeautyRepository imUserBeautyRepository;

    @Override
    public List<UserBeauty> listByCondition(UserBeautyParam condition) {
        UserBeauty userBeauty = new UserBeauty();
        BeanUtils.copyProperties(condition, userBeauty);
        return imUserBeautyRepository.findAll(Example.of(userBeauty));
    }

    @Override
    public Long countByCondition(UserBeautyParam condition) {
        UserBeauty userBeauty = new UserBeauty();
        BeanUtils.copyProperties(condition, userBeauty);
        return imUserBeautyRepository.count(Example.of(userBeauty));
    }

    @Override
    public Paged<UserBeauty> pageByCondition(UserBeautyParam condition, Integer pageNum, Integer pageSize) {
        UserBeauty userBeauty = new UserBeauty();
        BeanUtils.copyProperties(condition, userBeauty);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<UserBeauty> result = imUserBeautyRepository.findAll(Example.of(userBeauty), pageable);
        return Paged.<UserBeauty>builder()
                .list(result.getContent())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @Override
    public UserBeauty findById(Long id) {
        return imUserBeautyRepository.findById(id).orElse(null);
    }

    @Override
    public UserBeauty findByUsername(String username) {
        return imUserBeautyRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserBeauty findByUid(String uid) {
        return imUserBeautyRepository.findByUid(uid).orElse(null);
    }

    @Override
    public UserBeauty add(UserBeauty userBeauty) {
        return imUserBeautyRepository.save(userBeauty);
    }

    @Override
    public Integer addBatch(List<UserBeauty> userBeauties) {
        return imUserBeautyRepository.saveAll(userBeauties).size();
    }

    @Override
    public UserBeauty update(UserBeauty userBeauty) {
        return imUserBeautyRepository.save(userBeauty);
    }

    @Override
    public void deleteById(Long id) {
        imUserBeautyRepository.deleteById(id);
    }

    @Override
    public void deleteByUid(String uid) {
        imUserBeautyRepository.deleteByUid(uid);
    }
}
