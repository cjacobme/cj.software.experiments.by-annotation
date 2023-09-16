package cj.software.experiments.annotation.control.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Summary
    private String description;

    @NotNull
    @DecimalMin("0.01")
    private Double singlePrice;

    @NotNull
    @Min(1)
    private Integer count;

    @NotNull
    @DecimalMin("0.01")
    @Summary
    private Double totalPrice;

    private Item() {
    }

    public String getDescription() {
        return description;
    }

    public Double getSinglePrice() {
        return singlePrice;
    }

    public Integer getCount() {
        return count;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected Item instance;

        protected Builder() {
            instance = new Item();
        }

        public Item build() {
            Item result = instance;
            instance = null;
            return result;
        }

        public Builder withDescription(String description) {
            instance.description = description;
            return this;
        }

        public Builder withSinglePrice(double singlePrice) {
            instance.singlePrice = singlePrice;
            return this;
        }

        public Builder withCount(int count) {
            instance.count = count;
            return this;
        }

        public Builder withTotalPrice(double totalPrice) {
            instance.totalPrice = totalPrice;
            return this;
        }
    }
}