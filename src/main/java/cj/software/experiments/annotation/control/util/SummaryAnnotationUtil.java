package cj.software.experiments.annotation.control.util;

import cj.software.experiments.annotation.control.entity.Summary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SummaryAnnotationUtil {

    private static final String SEARCHED_CLASSNAME = Summary.class.getName();

    private final ValidationAnnotationsHelper validationAnnotationsHelper = new ValidationAnnotationsHelper();

    public boolean isSummaryAnnotated(Field field) {
        boolean result = false;
        Annotation[] annotations = field.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            String[] groups = validationAnnotationsHelper.determineGroups(annotation);
            if (contains(groups)) {
                result = true;
                break;
            }
        }
        return result;
    }


    private boolean contains(String[] groups) {
        boolean result = false;
        for (String checked : groups) {
            if (checked.equals(SEARCHED_CLASSNAME)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
