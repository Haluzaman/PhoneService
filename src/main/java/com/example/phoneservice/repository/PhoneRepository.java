package com.example.phoneservice.repository;

import com.example.phoneservice.db.PhoneDataSource;
import com.example.phoneservice.entities.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class PhoneRepository implements IPhoneRepository {

    private final PhoneDataSource phoneDataSource;

    @Autowired
    public PhoneRepository(PhoneDataSource dataSource) {
        this.phoneDataSource = dataSource;
    }

    @Override
    public List<Phone> findAllPhones() {
        var phoneList = this.phoneDataSource.getPhoneList();

        //remove phone with duplicate names
        Set<Phone> unique = new HashSet<>(phoneList);
        return new LinkedList<>(unique);
    }

    @Override
    public Optional<Phone> findPhoneById(Long id) {
        List<Phone> phoneList = this.phoneDataSource.getPhoneList();
        if (id == null || CollectionUtils.isEmpty(phoneList)) {
            return Optional.empty();
        }

        return phoneList.stream()
                        .filter(p -> p.getId().equals(id))
                        .findFirst();
    }

    @Override
    public Phone savePhone(Phone p) {
        return this.phoneDataSource.addPhone(p);
    }

    @Override
    public Phone saveOrUpdatePhone(Phone p) {
        return this.phoneDataSource.addOrUpdatePhone(p);
    }

    @Override
    public Phone deleteById(Long id) {
        return this.phoneDataSource.deletePhoneById(id);
    }

}
