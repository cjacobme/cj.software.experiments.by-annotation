package cj.software.experiments.annotation.control.entity;

public class OfferBuilder extends Offer.Builder{
    public OfferBuilder() {
        super
                .withCustomer(new CustomerBuilder().build())
                .withTotalPrice(33.45)
                .withItems(
                        new ItemBuilder().build(),
                        Item.builder()
                                .withDescription("hammers")
                                .withSinglePrice(12.5)
                                .withCount(2)
                                .withTotalPrice(25.0)
                                .build()
                );
    }
}
