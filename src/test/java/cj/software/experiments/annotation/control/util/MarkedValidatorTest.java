package cj.software.experiments.annotation.control.util;

import cj.software.experiments.annotation.control.entity.Customer;
import cj.software.experiments.annotation.control.entity.Item;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class MarkedValidatorTest {
    private static MarkedValidator markedValidator;

    @BeforeAll
    static void createValidator() {
        markedValidator = new MarkedValidator();
    }

    @Test
    void itemValidOnlyForSummary() {
        Item item = Item.builder()
                .withDescription("description")
                .withTotalPrice(13.14)
                .build();
        Set<ConstraintViolation<Item>> violations = markedValidator.validate(item);
        assertThat(violations).as("violations").isEmpty();
    }

    @Test
    void itemDescriptionMissing() {
        Item item = Item.builder()
                .withTotalPrice(13.14)
                .build();
        Set<ConstraintViolation<Item>> violations = markedValidator.validate(item);
        List<ErrorMessage> errorMessages = toErrorMessages(violations);
        assertThat(errorMessages)
                .as("error messages")
                .extracting("propertyPath", "messageTemplate", "invalidValue")
                .containsExactlyInAnyOrder(tuple("description", "{jakarta.validation.constraints.NotBlank.message}", null));
    }

    @Test
    void itemTotalPriceTooSmall() {
        Item item = Item.builder()
                .withDescription("description")
                .withTotalPrice(-15.2)
                .build();
        Set<ConstraintViolation<Item>> violations = markedValidator.validate(item);
        List<ErrorMessage> errorMessages = toErrorMessages(violations);
        assertThat(errorMessages)
                .as("error messages")
                .extracting("propertyPath", "messageTemplate", "invalidValue")
                .containsExactlyInAnyOrder(tuple("totalPrice", "{jakarta.validation.constraints.DecimalMin.message}", -15.2));
    }

    @Test
    void customerValidOnlyForSummary() {
        Customer customer = Customer.builder().withMail("a.b@c.d").build();
        Set<ConstraintViolation<Customer>> violations = markedValidator.validate(customer);
        assertThat(violations).as("constraint violations").isEmpty();
    }

    @Test
    void customerMailMissing() {
        Customer customer = Customer.builder().build();
        Set<ConstraintViolation<Customer>> violations = markedValidator.validate(customer);
        List<ErrorMessage> errorMessages = toErrorMessages(violations);
        assertThat(errorMessages)
                .as("error messages")
                .extracting("propertyPath", "messageTemplate", "invalidValue")
                .containsExactlyInAnyOrder(tuple("mail", "{jakarta.validation.constraints.NotBlank.message}", null));
    }

    private <T> List<ErrorMessage> toErrorMessages(Set<ConstraintViolation<T>> violations) {
        List<ErrorMessage> result = new ArrayList<>(violations.size());
        for (ConstraintViolation<?> violation : violations) {
            ErrorMessage errorMessage = new ErrorMessage(violation);
            result.add(errorMessage);
        }
        return result;
    }

    private static class ErrorMessage implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private final String propertyPath;

        private final String messageTemplate;

        private final Object invalidValue;

        ErrorMessage(ConstraintViolation<?> constraintViolation) {
            this.propertyPath = constraintViolation.getPropertyPath().toString();
            this.messageTemplate = constraintViolation.getMessageTemplate();
            this.invalidValue = constraintViolation.getInvalidValue();
        }

        public String getPropertyPath() {
            return propertyPath;
        }

        public String getMessageTemplate() {
            return messageTemplate;
        }

        public Object getInvalidValue() {
            return invalidValue;
        }
    }
}
