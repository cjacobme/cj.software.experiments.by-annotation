package cj.software.experiments.annotation.control.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = Item.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }


    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        Item.Builder builder = Item.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                Item.class);

        Item instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getDescription()).as("description").isNull();
        softy.assertThat(instance.getSinglePrice()).as("single price").isNull();
        softy.assertThat(instance.getCount()).as("count").isNull();
        softy.assertThat(instance.getTotalPrice()).as("total price").isNull();
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        String description = "_description";
        double singlePrice = 12.31;
        int count = 2;
        double totalPrice = 24.62;
        Item instance = Item.builder()
                .withDescription(description)
                .withSinglePrice(singlePrice)
                .withCount(count)
                .withTotalPrice(totalPrice)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getDescription()).as("description").isEqualTo(description);
        softy.assertThat(instance.getSinglePrice()).as("single price").isEqualTo(singlePrice);
        softy.assertThat(instance.getCount()).as("count").isEqualTo(count);
        softy.assertThat(instance.getTotalPrice()).as("total price").isEqualTo(totalPrice);
        softy.assertAll();
    }

    @Test
    void defaultIsValid() {
        Item instance = new ItemBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Item>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }
}