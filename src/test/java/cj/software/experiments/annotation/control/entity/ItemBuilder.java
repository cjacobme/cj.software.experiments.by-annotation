package cj.software.experiments.annotation.control.entity;

public class ItemBuilder extends Item.Builder{
    public ItemBuilder() {
        super.withDescription("pencils")
                .withSinglePrice(0.30)
                .withCount(5)
                .withTotalPrice(1.50);
    }
}
