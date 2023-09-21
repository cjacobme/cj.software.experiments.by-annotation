package cj.software.experiments.annotation.control.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

public class MarkedFieldsCollector {
    private final SummaryAnnotationUtil summaryAnnotationUtil = new SummaryAnnotationUtil();

    public Set<String> collect(Class<?> clazz) {
        Set<String> result = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers()) && summaryAnnotationUtil.isSummaryAnnotated(field)) {
                Class<?> fieldClass = field.getType();
                String classname = fieldClass.getName();
                String name = field.getName();
                if (classname.startsWith("cj.software.experiments.annotation.control.entity")) {
                    Set<String> fromEntity = collectFromEntity(name, fieldClass);
                    result.addAll(fromEntity);
                } else if (classname.startsWith("java.util.List")) {
                    Set<String> fromList = collectFromList(field, name);
                    result.addAll(fromList);
                } else {
                    result.add(name);
                }
            }
        }
        return result;
    }

    private Set<String> collectFromEntity(String fieldName, Class<?> fieldClass) {
        Set<String> markedOfField = collect(fieldClass);
        Set<String> result = HashSet.newHashSet(markedOfField.size());
        for (String marked : markedOfField) {
            String entry = fieldName + "." + marked;
            result.add(entry);
        }
        return result;
    }

    private Set<String> collectFromList(Field field, String fieldName) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Class<?> parameterizedClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        String parameterizedClassName = parameterizedClass.getName();
        Set<String> result;
        if (parameterizedClassName.startsWith("cj.software.experiments.annotation.control.entity")) {
            result = collectFromEntity(fieldName, parameterizedClass);
        } else {
            result = Set.of();
        }
        return result;
    }
}
