package cn.abner.funchat.service.impl;

import cn.abner.funchat.common.constants.Constants;
import cn.abner.funchat.common.Paged;
import cn.abner.funchat.common.constants.RedisConstants;
import cn.abner.funchat.entity.ImUser;
import cn.abner.funchat.entity.dto.UserTokenDTO;
import cn.abner.funchat.entity.enums.UserJoinTypeEnum;
import cn.abner.funchat.entity.param.UserParam;
import cn.abner.funchat.entity.param.UserRegister;
import cn.abner.funchat.entity.enums.UserStatusEnum;
import cn.abner.funchat.exception.BizException;
import cn.abner.funchat.repository.ImUserRepository;
import cn.abner.funchat.service.ImUserService;
import cn.abner.funchat.util.EntityUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.MD5;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
public class ImUserServiceImpl implements ImUserService {
    private final Logger logger = LoggerFactory.getLogger(ImUserServiceImpl.class);
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ImUserRepository imUserRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<ImUser> listByCondition(UserParam condition) {
        ImUser user = new ImUser();
        BeanUtils.copyProperties(condition, user);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("nickname", ExampleMatcher.GenericPropertyMatchers.contains());
        return imUserRepository.findAll(Example.of(user, matcher));
    }

    @Override
    public Long countByCondition(UserParam condition) {
        ImUser user = new ImUser();
        BeanUtils.copyProperties(condition, user);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("nickname", ExampleMatcher.GenericPropertyMatchers.contains());
        return imUserRepository.count(Example.of(user, matcher));
    }

    @Override
    public Paged<ImUser> pageByCondition(UserParam condition, Integer pageNum, Integer pageSize) {
        ImUser user = new ImUser();
        BeanUtils.copyProperties(condition, user);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("nickname", ExampleMatcher.GenericPropertyMatchers.contains());
        Page<ImUser> result = imUserRepository.findAll(Example.of(user, matcher), pageable);
        return Paged.<ImUser>builder()
                .list(result.getContent())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @Override
    public ImUser findById(String uid) {
        return imUserRepository.findById(uid).orElse(null);
    }

    @Override
    public ImUser findByUsername(String username) {
        return imUserRepository.findByUsername(username).orElse(null);
    }

    @Override
    public ImUser register(UserRegister register) {
        ImUser userExisted = imUserRepository.findByUsername(register.getUsername()).orElse(null);
        if (userExisted != null) {
            logger.error("用户名已被占用");
            throw new BizException("用户名已被占用！");
        }
        if (register.getNickname() == null) {
            register.setNickname(Constants.NICK_PREFIX + UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        }
        ImUser user = ImUser.builder()
                .uid(Constants.USER_PREFIX + RandomUtil.randomNumbers(12))
                .username(register.getUsername())
                .password(BCrypt.hashpw(register.getPassword(), Constants.PWD_SALT))
                .nickname(register.getNickname())
                .email(register.getEmail())
                .joinType(UserJoinTypeEnum.AGREE.getType())
                .status(UserStatusEnum.ENABLE.getStatus())
                .build();

        user = imUserRepository.saveAndFlush(user);
        user.setPassword(null);
        return user;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();
        Optional<ImUser> userOptional = imUserRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            ImUser user = userOptional.get();
            if (!BCrypt.checkpw(password, user.getPassword())) {
                logger.error("账号或密码错误");
                throw new BizException("账号或密码错误");
            }
            if (UserStatusEnum.DISABLE.getStatus().equals(user.getStatus())) {
                logger.error("账号已禁用");
                throw new BizException("账号已禁用");
            }
            String token = MD5.create().digestHex(user.getUid() + RandomUtil.randomString(20));
            UserTokenDTO userToken = UserTokenDTO.builder()
                    .uid(user.getUid())
                    .username(user.getUsername())
                    .token(token)
                    .build();
            // TODO 登录状态查询，已登录则不让继续登录

            // 保存登录信息到 redis
            redisTemplate.opsForValue().set(RedisConstants.WS_TOKEN_USERID + user.getUid(), token, RedisConstants.TOKEN_EXPIRE_SECOND, TimeUnit.SECONDS);
            Map<String, Object> tokenMap = EntityUtil.convertEntity2Map(userToken);
            redisTemplate.opsForHash().putAll(RedisConstants.WS_TOKEN + token, tokenMap);
            redisTemplate.expire(RedisConstants.WS_TOKEN + token, RedisConstants.TOKEN_EXPIRE_SECOND, TimeUnit.SECONDS);
            // TODO 查询群组

            // TODO 查询联系人

            result.put("userToken", userToken);
            result.put("user", user);
            return result;
        } else {
            throw new BizException("用户不存在");
        }
    }

    @Override
    public ImUser add(ImUser user) {
        return imUserRepository.save(user);
    }

    @Override
    public Integer addBatch(List<ImUser> users) {
        return imUserRepository.saveAll(users).size();
    }

    @Override
    public ImUser update(ImUser user) {
        return imUserRepository.save(user);
    }

    @Override
    public void deleteById(String uid) {
        imUserRepository.deleteById(uid);
    }

    @Override
    public void deleteByUsername(String username) {
        imUserRepository.deleteByUsername(username);
    }

    @Override
    public ImUser getCurrentUser() {
        String token = request.getHeader(Constants.TOKEN_HEADER);
        if (token == null) {
            throw new BizException("无效的用户信息");
        }
        String userId = (String) redisTemplate.opsForHash().get(RedisConstants.WS_TOKEN + token, "uid");
        return findById(userId);
    }
}
