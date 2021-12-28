package com.nulp.FlowerProject.dao;

import com.nulp.FlowerProject.models.Bouquet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BouquetDaoTest {

    @Autowired
    BouquetDao bouquetDao;

    @Test
    @Rollback(false)
    @Order(1)
    public void testCreateBouquet() {
          Bouquet bouquet = bouquetDao.save(new Bouquet("newBouquet"));
          assertThat(bouquet.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void testFindBouquetById() {
        Bouquet bouquetID = bouquetDao.findById((long) 2).orElse(null);
        assertThat(bouquetID.getId()).isEqualTo(2);
    }

    @Test
    @Order(3)
    public void testListBouquet() {
        List<Bouquet> bouquets = (List<Bouquet>) bouquetDao.findAll();
        assertThat(bouquets).size().isGreaterThan(0);
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void testUpdateBouquet() {
        Bouquet bouquet = bouquetDao.findById((long) 2).orElse(null);
        bouquet.setPrice(100);

        bouquetDao.save(bouquet);

        Bouquet updatedBouquet = bouquetDao.findById((long) 1).orElse(null);

        assertThat(updatedBouquet.getPrice()).isEqualTo(100);
    }

    @Test
    @Rollback(false)
    @Order(5)
    public void testDeleteBouquet() {
        Bouquet bouquet = bouquetDao.findById((long) 2).orElse(null);

        bouquetDao.deleteById(bouquet.getId());

        Bouquet deletedBouquet = bouquetDao.findById((long) 2).orElse(null);

        assertThat(deletedBouquet).isNull();
    }
}