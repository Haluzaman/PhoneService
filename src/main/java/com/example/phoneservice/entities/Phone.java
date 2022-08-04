package com.example.phoneservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * Class representing Phone entity in the database or entity to be returned by API
 *
 * @Author Lubos Finka
 * */
@Data
@ToString
@AllArgsConstructor
public class Phone {

    private final Long id;

    @NotEmpty(message = "name of the phone cannot be empty")
    private final String name;

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Phone)) {
            return false;
        }

        Phone obj = (Phone) o;
        return obj.name.equals(this.name);
    }

}
