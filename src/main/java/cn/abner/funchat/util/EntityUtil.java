package cn.abner.funchat.util;

import cn.abner.funchat.annotation.FieldIgnore;
import cn.abner.funchat.exception.BizException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity 工具类
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/24
 */
public class EntityUtil {
    /**
     * 将 Entity 按字段转为 Map
     * @param entity Object
     * @return Map<String, Object>
     */
    public static Map<String, Object> convertEntity2Map(Object entity){
        Map<String, Object> result = new HashMap<>();
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // 如有忽略注解，则不处理
            FieldIgnore annotation = field.getAnnotation(FieldIgnore.class);
            if (annotation == null) {
                String fieldName = field.getName();
                Object fieldValue;
                try {
                    fieldValue = field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                result.put(fieldName, fieldValue);
            }
        }
        return result;
    }

    public static <T> T convertMap2Entity(Map<String, Object> map, Class<T> clazz) throws Exception {
        T entity = clazz.getDeclaredConstructor().newInstance();
        map.forEach((fieldName, fieldValue) -> {
            Field field;
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(entity, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return entity;
    }
}
