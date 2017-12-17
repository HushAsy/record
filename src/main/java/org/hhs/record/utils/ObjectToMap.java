package org.hhs.record.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class ObjectToMap {
      public static Map<String, Object> objectToMap(Object obj) {
           Map<String, Object> map = new HashMap();
           Class<?> clazz = obj.getClass();
           for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
               Object value = null;
               try {
                   value = field.get(obj);
               } catch (IllegalAccessException e) {
                   log.error("IllegalAccessException", e);
               }
               map.put(fieldName, value);
           }
           return map;
      }
}
