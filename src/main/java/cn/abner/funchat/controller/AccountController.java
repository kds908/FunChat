package cn.abner.funchat.controller;

import cn.abner.funchat.common.constants.RedisConstants;
import cn.abner.funchat.common.ResponseResult;
import cn.abner.funchat.entity.ImUser;
import cn.abner.funchat.entity.dto.UserTokenDTO;
import cn.abner.funchat.entity.param.UserLogin;
import cn.abner.funchat.entity.param.UserRegister;
import cn.abner.funchat.entity.vo.UserInfoVO;
import cn.abner.funchat.exception.BizException;
import cn.abner.funchat.service.ImUserService;
import com.wf.captcha.SpecCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Account Controller 账号管理 controller
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
@RestController
@RequestMapping("/account")
@Validated
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ImUserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取验证码
     *
     * @return base64 img
     */
    @GetMapping("/captcha")
    public ResponseResult<Object> captcha() {
        logger.info("get captcha");
        SpecCaptcha specCaptcha = new SpecCaptcha(100, 25, 4);
        String verifyCode = specCaptcha.text().toLowerCase();
        logger.info("verify code: {}", verifyCode);
        String identity = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        String storeKey = RedisConstants.CAPTCHA_VERIFY + identity;
        redisTemplate.opsForValue().set(storeKey, verifyCode, 10, TimeUnit.MINUTES);
        Map<String, Object> captchaMap = new LinkedHashMap<>();
        captchaMap.put("identity", identity);
        captchaMap.put("captchaImage", specCaptcha.toBase64());
        return ResponseResult.success(captchaMap);
    }

    /**
     * 用户注册
     *
     * @param register {@link UserRegister}
     * @return result
     */
    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody UserRegister register) {
        logger.info("register user: {}", register);
        String key = RedisConstants.CAPTCHA_VERIFY + register.getIdentity();
        Object storedCaptcha = redisTemplate.opsForValue().get(key);
        if (storedCaptcha == null) {
            logger.error("验证码超时失效");
            throw new BizException("验证码失效");
        }
        if (!register.getCaptchaCode().equalsIgnoreCase(storedCaptcha.toString())) {
            logger.error("验证码错误");
            throw new BizException("验证码错误");
        }
        ImUser user = userService.register(register);
        redisTemplate.delete(key);
        return ResponseResult.success(user);
    }

    @PostMapping("/login")
    public ResponseResult<Object> login(@RequestBody UserLogin login) {
        logger.info("login user: {}", login);
        String key = RedisConstants.CAPTCHA_VERIFY + login.getIdentity();
        Object storedCaptcha = redisTemplate.opsForValue().get(key);
        if (storedCaptcha == null) {
            logger.error("验证码超时失效");
            throw new BizException("验证码失效");
        }
        if (!login.getCaptchaCode().equalsIgnoreCase(storedCaptcha.toString())) {
            logger.error("验证码错误");
            throw new BizException("验证码错误!");
        }
        Map<String, Object> loginMap = userService.login(login.getUsername(), login.getPassword());
        redisTemplate.delete(key);
        UserInfoVO userInfoVO = new UserInfoVO();
        ImUser user = (ImUser) loginMap.get("user");
        UserTokenDTO userToken = (UserTokenDTO) loginMap.get("userToken");
        BeanUtils.copyProperties(user, userInfoVO, "password");
        BeanUtils.copyProperties(userToken, userInfoVO);
        return ResponseResult.success(userInfoVO);
    }

    @GetMapping("/info")
    public ResponseResult<UserInfoVO> info() {

        return null;
    }

    /**
     * TODO 系统设置
     * @return
     */
    @GetMapping("systemSettings")
    public ResponseResult<Object> systemSettings() {
        return null;
    }
}
