package com.example.phoneservice.repository;

import com.example.phoneservice.db.PhoneDataSource;
import com.example.phoneservice.entities.Phone;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PhoneRepositoryTest {

    @MockBean
    PhoneDataSource dataSource;

    @Autowired
    public IPhoneRepository repository;

    private List<Phone> phoneList = Arrays.asList(
            new Phone(1L, "Samsung s1"),
            new Phone(2L, "Samsung s2"),
            new Phone(3L, "Samsung s3"),
            new Phone(4L, "Samsung s1"),
            new Phone(5L, "Samsung s1")
    );

    @Test
    public void findPhoneByIdSuccess() {
        Mockito.when(this.dataSource.getPhoneList()).thenReturn(phoneList);
        var found = this.repository.findPhoneById(1L);
        assert found.isPresent() && found.get().equals(dataSource.getPhoneList().get(0));
    }

    @Test
    public void findAllPhonesSuccess() {
        Mockito.when(this.dataSource.getPhoneList()).thenReturn(phoneList);
        List<Phone> list = this.repository.findAllPhones();

        assert !CollectionUtils.isEmpty(list) && list.size() == 3;

        Set<Phone> unique = new HashSet<>(list);
        assert unique.size() == list.size();
    }

}
