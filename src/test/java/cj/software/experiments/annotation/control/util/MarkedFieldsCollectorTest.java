package cj.software.experiments.annotation.control.util;

import cj.software.experiments.annotation.control.entity.Customer;
import cj.software.experiments.annotation.control.entity.Item;
import cj.software.experiments.annotation.control.entity.Offer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MarkedFieldsCollectorTest {
    private static MarkedFieldsCollector markedFieldsCollector;

    @BeforeAll
    static void createCollector() {
        markedFieldsCollector = new MarkedFieldsCollector();
    }

    @Test
    void forItem() {
        Set<String> marked = markedFieldsCollector.collect(Item.class);
        assertThat(marked).as("marked").isEqualTo(Set.of("description", "totalPrice"));
    }

    @Test
    void forCustomer() {
        Set<String> marked = markedFieldsCollector.collect(Customer.class);
        assertThat(marked).as("marked").isEqualTo(Set.of("mail"));
    }

    @Test
    void forOffer() {
        Set<String> marked = markedFieldsCollector.collect(Offer.class);
        assertThat(marked).as("marked").isEqualTo(Set.of(
                "items.description",
                "items.totalPrice",
                "customer.mail",
                "totalPrice"
        ));
    }
}
