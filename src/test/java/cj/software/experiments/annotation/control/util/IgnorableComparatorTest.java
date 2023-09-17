package cj.software.experiments.annotation.control.util;

import cj.software.experiments.annotation.control.entity.Item;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class IgnorableComparatorTest {
    private static final IgnorableFieldsCollector collector = new IgnorableFieldsCollector();

    @Test
    void equalItems() {
        Item item1 = Item.builder()
                .withCount(5)
                .withDescription("item 1")
                .withSinglePrice(15.4)
                .withTotalPrice(67.0)
                .build();
        Item item2 = Item.builder()
                .withCount(6)
                .withSinglePrice(15.5)
                .withDescription("item 1")
                .withTotalPrice(67.0)
                .build();

        Set<String> ignorable = collector.collect(Item.class);
        String[] ignore = toStringArray(ignorable);

        assertThat(item1)
                .usingRecursiveComparison()
                .ignoringFields(ignore)
                .isEqualTo(item2);
    }

    private String[] toStringArray (Set<String> source) {
        int size = source.size();
        String[] result = new String[size];
        result = source.toArray(result);
        return result;
    }

    @Test
    void unequalDescription() {
        Item item1 = Item.builder()
                .withCount(5)
                .withDescription("item 1")
                .withSinglePrice(15.4)
                .withTotalPrice(67.0)
                .build();
        Item item2 = Item.builder()
                .withCount(6)
                .withSinglePrice(15.5)
                .withDescription("item 2")
                .withTotalPrice(67.0)
                .build();

        Set<String> ignorable = collector.collect(Item.class);
        String[] ignore = toStringArray(ignorable);

        assertThat(item1)
                .usingRecursiveComparison()
                .ignoringFields(ignore)
                .isNotEqualTo(item2);
    }

    @Test
    void unequalTotalPrice() {
        Item item1 = Item.builder()
                .withCount(5)
                .withDescription("item 1")
                .withSinglePrice(15.4)
                .withTotalPrice(67.0)
                .build();
        Item item2 = Item.builder()
                .withCount(6)
                .withSinglePrice(15.5)
                .withDescription("item 1")
                .withTotalPrice(67.1)
                .build();

        Set<String> ignorable = collector.collect(Item.class);
        String[] ignore = toStringArray(ignorable);

        assertThat(item1)
                .usingRecursiveComparison()
                .ignoringFields(ignore)
                .isNotEqualTo(item2);

    }
}
