package com.example.phoneservice.service;

import com.example.phoneservice.entities.Phone;
import com.example.phoneservice.repository.IPhoneRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneServiceImpl implements IPhoneService {

    private final IPhoneRepository phoneRepository;

    public PhoneServiceImpl(IPhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public List<Phone> getAllPhones() {
        return this.phoneRepository.findAllPhones();
    }

    @Override
    public List<Phone> getAllPhonesSorted(String sortOrder) {
        List<Phone> phoneList = this.phoneRepository.findAllPhones();

        if ("0".equalsIgnoreCase(sortOrder) || "desc".equalsIgnoreCase(sortOrder)) {
            phoneList.sort(Comparator.comparing(Phone::getName).reversed());
        } else {
            phoneList.sort(Comparator.comparing(Phone::getName));
        }

        return phoneList;
    }

    @Override
    public Optional<Phone> findPhoneById(Long id) {
        return this.phoneRepository.findPhoneById(id);
    }

    @Override
    public Phone savePhone(Phone p) {
        return this.phoneRepository.savePhone(p);
    }

    @Override
    public Phone deletePhoneById(Long id) {
        return this.phoneRepository.deleteById(id);
    }

    @Override
    public Phone createOrUpdatePhone(Phone phone) {
        if (phone == null) {
            return null;
        }

        return this.phoneRepository.saveOrUpdatePhone(phone);
    }

}
