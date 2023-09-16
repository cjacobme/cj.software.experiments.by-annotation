package cj.software.experiments.annotation.control.util;

import cj.software.experiments.annotation.control.entity.Summary;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class IgnorableFieldsCollector {
    public Set<String> collect(Class<?> clazz) {
        Set<String> result = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                Summary summary = field.getAnnotation(Summary.class);
                if (summary == null) {
                    String name = field.getName();
                    result.add(name);
                }
            }
        }
        return result;
    }
}
