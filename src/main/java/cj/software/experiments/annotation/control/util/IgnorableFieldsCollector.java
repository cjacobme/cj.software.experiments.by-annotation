package cj.software.experiments.annotation.control.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

public class IgnorableFieldsCollector {

    private final SummaryAnnotationUtil summaryAnnotationUtil = new SummaryAnnotationUtil();

    public Set<String> collect(Class<?> clazz) {
        Set<String> result = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                String name = field.getName();
                boolean summary = summaryAnnotationUtil.isSummaryAnnotated(field);
                if (!summary) {
                    result.add(name);
                } else {
                    Set<String> recursive = recursive(field, name);
                    if (recursive != null && !recursive.isEmpty()) {
                        result.addAll(recursive);
                    }
                }
            }
        }
        return result;
    }

    private Set<String> recursive(Field field, String name) {
        Class<?> fieldClass = field.getType();
        String classname = fieldClass.getName();
        Set<String> result;
        if (classname.startsWith("cj.software.experiments.annotation.control.entity")) {
            result = collectFromEntity(name, fieldClass);
        } else if (classname.equals("java.util.List")) {
            result = collectFromList(field, name);
        } else {
            result = Set.of();
        }
        return result;
    }

    private Set<String> collectFromEntity(String fieldName, Class<?> fieldClass) {
        Set<String> ignorablesOfField = collect(fieldClass);
        Set<String> result = HashSet.newHashSet(ignorablesOfField.size());
        for (String ignorable : ignorablesOfField) {
            String entry = fieldName + "." + ignorable;
            result.add(entry);
        }
        return result;
    }

    private Set<String> collectFromList(Field field, String name) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Class<?> parameterizedClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        String parameterizedClassName = parameterizedClass.getName();
        Set<String> result;
        if (parameterizedClassName.startsWith("cj.software.experiments.annotation.control.entity")) {
            result = collectFromEntity(name, parameterizedClass);
        } else {
            result = Set.of();
        }
        return result;
    }
}
