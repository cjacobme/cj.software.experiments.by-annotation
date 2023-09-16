package cj.software.experiments.annotation.control.entity;

import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;

    @Summary
    @NotBlank
    private String mail;

    private Customer() {
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected Customer instance;

        protected Builder() {
            instance = new Customer();
        }

        public Customer build() {
            Customer result = instance;
            instance = null;
            return result;
        }

        public Builder withName(String name) {
            instance.name = name;
            return this;
        }

        public Builder withMail(String mail) {
            instance.mail = mail;
            return this;
        }
    }
}