package cj.software.experiments.annotation.control.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Offer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Valid
    @Summary
    private Customer customer;

    @NotEmpty
    @Summary
    private final List<Item> items = new ArrayList<>();

    @NotNull
    @DecimalMin("0.01")
    @Summary
    private Double totalPrice;

    private Offer() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected Offer instance;

        protected Builder() {
            instance = new Offer();
        }

        public Offer build() {
            Offer result = instance;
            instance = null;
            return result;
        }

        public Builder withCustomer(Customer customer) {
            instance.customer = customer;
            return this;
        }

        public Builder withItems(Item... items) {
            instance.items.clear();
            if (items != null) {
                instance.items.addAll(Arrays.asList(items));
            }
            return this;
        }

        public Builder withTotalPrice(double totalPrice) {
            instance.totalPrice = totalPrice;
            return this;
        }
    }
}