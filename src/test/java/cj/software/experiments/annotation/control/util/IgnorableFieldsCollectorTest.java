package cj.software.experiments.annotation.control.util;

import static org.assertj.core.api.Assertions.*;

import cj.software.experiments.annotation.control.entity.Customer;
import cj.software.experiments.annotation.control.entity.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

class IgnorableFieldsCollectorTest {
    private static IgnorableFieldsCollector collector;

    @BeforeAll
    static void createCollector() {
        collector = new IgnorableFieldsCollector();
    }

    @Test
    void forItem() {
        Set<String> ignorables = collector.collect(Item.class);
        assertThat(ignorables).as("ignorables").isEqualTo(Set.of("singlePrice", "count"));
    }

    @Test
    void forCustomer() {
        Set<String> ignorables = collector.collect(Customer.class);
        assertThat(ignorables).as("ignorables").isEqualTo(Set.of("name"));
    }
}

