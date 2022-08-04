package com.example.phoneservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing list of {@link Phone} etities returned by the REST API
 *
 * @Author Lubos Finka
 * */
@Data
@ToString
public class PhoneDTO {

    private final List<Phone> phoneList;

    public PhoneDTO() {
        this.phoneList = new LinkedList<>();
    }

    public PhoneDTO(List<Phone> phones) {
        this.phoneList = phones;
    }

    public void addPhone(Phone p) {
        this.phoneList.add(p);
    }

    public void removePhone(Phone p) {
        this.phoneList.remove(p);
    }

}
