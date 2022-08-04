package com.example.phoneservice.dataSource;

import com.example.phoneservice.db.PhoneDataSource;
import com.example.phoneservice.entities.Phone;
import com.example.phoneservice.exception.PhoneAlreadyExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PhoneDataSourceTest {

    @Autowired
    PhoneDataSource dataSource;

    @Test()
    public void onlyAddPhoneTest() {
        Phone p = null;
        Phone added = this.dataSource.addPhone(p);
        assert added == null;

        p = new Phone(null, "Sony Xperia Z1");
        added = this.dataSource.addPhone(p);

        assert p.getName().equals(added.getName()) && added.getId() != null;

        String expectedMessage = "Phone with id: 1 already exists!";
        try {
            final var existingPhone = new Phone(1L, "");
            this.dataSource.addPhone(existingPhone);
            fail();
        } catch (PhoneAlreadyExistsException e) {
            assertTrue(expectedMessage.equalsIgnoreCase(e.getMessage()));
        }

    }

    @Test
    public void addPhoneTest() {
        Phone p = null;
        Phone added = this.dataSource.addOrUpdatePhone(p);
        assert added == null;

        p = new Phone(1L, "Sony Xperia Z1");
        added = this.dataSource.addOrUpdatePhone(p);

        assert p.getName().equals(added.getName()) && added.getId().equals(p.getId());

        //should create new with new Id
        p = new Phone(null, "Sony Xperia Z2");
        added = this.dataSource.addOrUpdatePhone(p);

        assert added.getId() != null && added.getName().equals(p.getName());

        //modify existing
        Long oldId = added.getId();
        String oldName = added.getName();
        Phone pNew = new Phone(oldId, "Sony Xperia Z3");
        added = this.dataSource.addOrUpdatePhone(pNew);

        assert added.getId().equals(oldId) && !oldName.equals(added.getName()) && added.getName().equals("Sony Xperia Z3");
    }

    @Test
    public void removalTest() {
        Phone p = this.dataSource.getPhoneList().get(0);
        Phone deleted = this.dataSource.deletePhoneById(p.getId());

        assert p.getId().equals(deleted.getId()) && p.getName().equals(deleted.getName());

        Phone found = this.dataSource.getPhoneList().stream().filter(p1 -> p1.getId().equals(p.getId())).findFirst().orElse(null);
        assert found == null;
    }

}
