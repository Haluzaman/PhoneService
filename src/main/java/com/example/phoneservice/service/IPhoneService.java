package com.example.phoneservice.service;

import com.example.phoneservice.entities.Phone;

import java.util.List;
import java.util.Optional;

/**
 * Service provides basic CRUD operations with phone objects
 *
 * @Author Lubos Finka
 * */
public interface IPhoneService {

    /**
     * Gets unique list of {@link Phone}.
     * List is not sorted
     * @return List of Phones
     */
    List<Phone> getAllPhones();

    /**
     * Gets unique list of {@link Phone}.
     * List is not sorted
     * @param sortOrder - in which order the list of phones should be sorted, default ascending order
     *                  - allowed values ascending: asc/1, descending: desc/0
     * @return List of Phones sorted based on parameter
     */
    List<Phone> getAllPhonesSorted(String sortOrder);

    /**
     * Gets Phone based on id.
     * @param id - id of the {@link Phone} to be found
     * @return phone
     */
    Optional<Phone> findPhoneById(Long id);

    /**
     * Saves {@link Phone}
     * @param p - Phone to be saved
     * @return saved Phone entity
     */
    Phone savePhone(Phone p);

    /**
     * Removes {@link Phone}
     * @param id - Phone to be removed
     * @return removed Phone entity
     */
    Phone deletePhoneById(Long id);

    /**
     * Creates {@link Phone} entity if it doesnt exist or creates entity
     * @param phone - Phone to be saved
     * @return created or Updated Phone entity
     */
    Phone createOrUpdatePhone(Phone phone);
}
