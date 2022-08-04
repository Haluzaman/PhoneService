package com.example.phoneservice.repository;

import com.example.phoneservice.entities.Phone;

import java.util.List;
import java.util.Optional;

/**
 * This class represents classic Spring data repository and it's functions
 *
 * @Author Lubos Finka
 * */
public interface IPhoneRepository {

    List<Phone> findAllPhones();

    Optional<Phone> findPhoneById(Long id);

    Phone savePhone(Phone p);

    Phone saveOrUpdatePhone(Phone p);

    Phone deleteById(Long id);

}
