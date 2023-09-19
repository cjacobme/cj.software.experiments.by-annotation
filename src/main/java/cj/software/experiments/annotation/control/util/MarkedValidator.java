package cj.software.experiments.annotation.control.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.HashSet;
import java.util.Set;

public class MarkedValidator {
    private final MarkedFieldsCollector markedFieldsCollector = new MarkedFieldsCollector();

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public <T> Set<ConstraintViolation<T>> validate(T object) {
        Set<ConstraintViolation<T>> result = new HashSet<>();
        Set<String> toBeValidateds = markedFieldsCollector.collect(object.getClass());
        for (String toBeValidated : toBeValidateds) {
            if (!toBeValidated.startsWith("items")) {   //TODO the collector should return a triple (class, parameterized class, path)
                Set<ConstraintViolation<T>> fieldViolations = validator.validateProperty(object, toBeValidated);
                result.addAll(fieldViolations);
            }
        }
        return result;
    }
}
