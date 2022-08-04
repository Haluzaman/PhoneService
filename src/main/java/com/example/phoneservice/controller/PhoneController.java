package com.example.phoneservice.controller;

import com.example.phoneservice.entities.Phone;
import com.example.phoneservice.entities.PhoneDTO;
import com.example.phoneservice.exception.InvalidRequestException;
import com.example.phoneservice.exception.PhoneAlreadyExistsException;
import com.example.phoneservice.exception.PhoneNotFoundException;
import com.example.phoneservice.service.IPhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/phones", produces = "application/json")
public class PhoneController {

    private final IPhoneService phoneService;

    @Autowired
    public PhoneController(IPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phone> getPhone(@PathVariable(name = "id") Long id) {
        Phone p = this.phoneService.findPhoneById(id).orElseThrow(() -> new PhoneNotFoundException(id));
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<PhoneDTO> getAllPhones(@RequestParam(name = "sort", required = false, defaultValue = "1") String sortOrder) {
        List<Phone> phones = this.phoneService.getAllPhonesSorted(sortOrder);

        return new ResponseEntity<>(new PhoneDTO(phones), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Phone> createPhone(@RequestBody @Valid Phone phone) {
        Phone p = this.phoneService.savePhone(phone);

        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Phone> deletePhone(@PathVariable(name = "id", required = true) Long id) {
        this.phoneService.deletePhoneById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Phone> putPhone(@RequestBody @Valid Phone phone) {
        if (phone == null || phone.getId() == null) {
            throw new InvalidRequestException("phone or phone id must not be null!");
        }

        Phone p = this.phoneService.createOrUpdatePhone(phone);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

}
