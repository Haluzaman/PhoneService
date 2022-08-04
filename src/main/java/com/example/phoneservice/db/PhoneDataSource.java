package com.example.phoneservice.db;

import com.example.phoneservice.entities.Phone;
import com.example.phoneservice.exception.PhoneAlreadyExistsException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class simulates source of data, for example Database.
 *
 * @Author Lubos Finka
 * */
@Component
public class PhoneDataSource {

    private long indexCounter;
    private final List<Phone> phoneList = new LinkedList<>();

    @PostConstruct
    private void init() {
        this.phoneList.add(new Phone(1L, "Samsung galaxy s20"));
        this.phoneList.add(new Phone(2L, "Samsung galaxy s21"));
        this.phoneList.add(new Phone(3L, "Samsung galaxy s21 fe"));
        this.phoneList.add(new Phone(4L, "Samsung galaxy s22"));
        this.phoneList.add(new Phone(5L, "Samsung galaxy s22 fe"));
        this.phoneList.add(new Phone(6L, "Nokia 3310"));
        this.phoneList.add(new Phone(7L, "Nokia 3310"));
        this.phoneList.add(new Phone(8L, "Nokia 3310"));

        this.indexCounter = this.phoneList.size();
    }

    public List<Phone> getPhoneList() {
        return this.phoneList;
    }

    public Phone addPhone(Phone p) throws PhoneAlreadyExistsException {
        if (p == null) return null;

        boolean exists = this.phoneList.stream().anyMatch(p1 -> p1.getId().equals(p.getId()));
        if (exists) {
            throw new PhoneAlreadyExistsException(p.getId());
        }

        Phone newPhone = new Phone(this.getNextId(), p.getName());
        this.phoneList.add(newPhone);
        return newPhone;
    }

    public Phone addOrUpdatePhone(Phone p) {
        if (p == null) return null;

        //create fresh entry
        if (p.getId() == null) {
            Phone newPhone = new Phone(this.getNextId(), p.getName());
            this.phoneList.add(newPhone);
            return newPhone;
        }

        Phone found = this.phoneList.stream().filter(phone -> phone.getId().equals(p.getId())).findFirst().orElse(null);
        //remove found phone entry which will be updated
        if (found != null) {
            this.phoneList.remove(found);
        }

        //create new phone entry
        this.phoneList.add(p);

        return p;
    }

    public Phone deletePhoneById(Long id) {
       Phone phone = this.phoneList.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
       this.phoneList.remove(phone);
       return phone;
    }

    private long getNextId() {
        this.indexCounter++;
        return this.indexCounter;
    }

}
