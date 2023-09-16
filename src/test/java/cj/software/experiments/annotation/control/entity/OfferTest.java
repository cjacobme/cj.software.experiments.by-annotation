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
import static org.mockito.Mockito.*;

class OfferTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = Offer.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }

    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        Offer.Builder builder = Offer.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                Offer.class);

        Offer instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getCustomer()).as("customer").isNull();
        softy.assertThat(instance.getItems()).as("items").isEmpty();
        softy.assertThat(instance.getTotalPrice()).as("total price").isNull();
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        Customer customer = mock(Customer.class);
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        double totalPrice = 47.11;
        Offer instance = Offer.builder()
                .withCustomer(customer)
                .withItems(item1, item2)
                .withTotalPrice(totalPrice)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getCustomer()).as("customer").isSameAs(customer);
        softy.assertThat(instance.getItems()).as("items").containsExactly(item1, item2);
        softy.assertThat(instance.getTotalPrice()).as("total price").isEqualTo(totalPrice);
        softy.assertAll();
    }

    @Test
    void defaultIsValid() {
        Offer instance = new OfferBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Offer>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }
}