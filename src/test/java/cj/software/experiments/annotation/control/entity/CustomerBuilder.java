package cj.software.experiments.annotation.control.entity;

public class CustomerBuilder extends Customer.Builder{
    public CustomerBuilder() {
        super
                .withName("Karl Durchschnitt")
                .withMail("karl.durchschnitt@web.de");
    }
}
