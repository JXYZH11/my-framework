package com.jxyzh11.test;

import com.alibaba.fastjson.JSONObject;
import com.jxyzh11.myframework.toolkit.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Test {

    public static void main(String[] args) {
        Student student = new Student();
        student.setId(1);
        student.setName("123");

        Map<String, Object> map = ObjectUtil.beanToMap(student);
        log.debug(map.get("name").toString());

        Student student1 = ObjectUtil.mapToBean(map, new Student());
        log.debug(student1.getName());

        JSONObject jsonObject = ObjectUtil.objectToJson(student1);
        log.debug(jsonObject.getString("name"));

        Student student2 = ObjectUtil.jsonToObject(jsonObject, Student.class);
        log.debug(student2.getName());

        Map<String, Object> map1 = ObjectUtil.jsonToObject(jsonObject, Map.class);
        log.debug(map1.get("name").toString());

        JSONObject jsonObject1 = ObjectUtil.objectToJson(map1);
        log.debug(jsonObject1.getString("name"));
    }

}
