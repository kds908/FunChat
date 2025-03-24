package cn.abner.funchat.aop.aspect;

import cn.abner.funchat.aop.annotation.GlobalInterceptor;
import cn.abner.funchat.common.constants.Constants;
import cn.abner.funchat.common.constants.RedisConstants;
import cn.abner.funchat.entity.dto.UserTokenDTO;
import cn.abner.funchat.exception.BizException;
import cn.abner.funchat.util.EntityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/24
 */
@Aspect
@Component("globalOperationAspect")
public class GlobalOperationAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Before("@annotation(cn.abner.funchat.aop.annotation.GlobalInterceptor)")
    public void checkPermit(JoinPoint point) {
        Method method = point.getSignature().getDeclaringType().getMethods()[0];
        GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
        if (interceptor == null) {
            return;
        }
        if (interceptor.checkLogin()) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                String token = request.getHeader(Constants.TOKEN_HEADER);
                if (token == null) {
                    throw new BizException("无效的用户信息");
                }

                Map<String, Object> entries = redisTemplate.opsForHash().entries(RedisConstants.WS_TOKEN + token)
                        .entrySet().stream()
                        .collect(Collectors.toMap(e -> String.valueOf(e.getKey()), Map.Entry::getValue));
                try {
                    UserTokenDTO userToken = EntityUtil.convertMap2Entity(entries, UserTokenDTO.class);
                    logger.info("userToken:{}", userToken);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }



}
