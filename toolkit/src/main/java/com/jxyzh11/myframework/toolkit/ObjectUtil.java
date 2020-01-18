package com.jxyzh11.myframework.toolkit;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;

/**
 * 对象工具类
 */
public class ObjectUtil {

    /**
     * bean转map
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        if (bean != null) {
            Map<String, Object> map = Maps.newHashMap();
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
            return map;
        }
        return null;
    }

    /**
     * map转bean
     *
     * @param map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        if (map != null) {
            BeanMap beanMap = BeanMap.create(bean);
            beanMap.putAll(map);
            return bean;
        }
        return null;
    }

    /**
     * object转json
     *
     * @param object
     * @return
     */
    public static JSONObject objectToJson(Object object) {
        if (object != null) {
            return (JSONObject) JSONObject.toJSON(object);
        }
        return null;
    }

    /**
     * json转object
     *
     * @param jsonObject
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(JSONObject jsonObject, Class<T> clazz) {
        if (jsonObject != null) {
            return JSONObject.toJavaObject(jsonObject, clazz);
        }
        return null;
    }
}
