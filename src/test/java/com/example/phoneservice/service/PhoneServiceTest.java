package com.example.phoneservice.service;

import com.example.phoneservice.db.PhoneDataSource;
import com.example.phoneservice.entities.Phone;
import com.example.phoneservice.repository.IPhoneRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.commons.util.CollectionUtils;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PhoneServiceTest {

    @MockBean
    public IPhoneRepository repository;

    @Autowired
    IPhoneService service;

    private List<Phone> phoneList = Arrays.asList(
            new Phone(1L, "Samsung s1"),
            new Phone(3L, "Samsung s3"),
            new Phone(2L, "Samsung s2")
    );

    @Test
    public void getAllPhonesSuccessTest() {
        Mockito.when(this.repository.findAllPhones()).thenReturn(phoneList);
        var l = this.service.getAllPhones();
        assert l.size() == phoneList.size();
    }

    @Test
    public void getAllPhonesSortedAscSuccessTest() {
        Mockito.when(this.repository.findAllPhones()).thenReturn(phoneList);

        var l = this.service.getAllPhonesSorted("asc");
        var newList = new LinkedList<>(this.phoneList);
        newList.sort(Comparator.comparing(Phone::getName));
        assertEquals(l, newList);

        l = this.service.getAllPhonesSorted("1");
        newList = new LinkedList<>(this.phoneList);
        newList.sort(Comparator.comparing(Phone::getName));
        assertEquals(l, newList);

        l = this.service.getAllPhonesSorted(null);
        newList = new LinkedList<>(this.phoneList);
        newList.sort(Comparator.comparing(Phone::getName));
        assertEquals(l, newList);
    }

    @Test
    public void getAllPhonesSortedDescSuccessTest() {
        Mockito.when(this.repository.findAllPhones()).thenReturn(phoneList);

        var l = this.service.getAllPhonesSorted("desc");
        var newList = new LinkedList<>(this.phoneList);
        newList.sort(Comparator.comparing(Phone::getName).reversed());
        assertEquals(l, newList);

        l = this.service.getAllPhonesSorted("0");
        newList = new LinkedList<>(this.phoneList);
        newList.sort(Comparator.comparing(Phone::getName).reversed());
        assertEquals(l, newList);
    }

}
