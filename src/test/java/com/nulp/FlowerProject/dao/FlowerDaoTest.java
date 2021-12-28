package com.nulp.FlowerProject.dao;

import com.nulp.FlowerProject.models.Bouquet;
import com.nulp.FlowerProject.models.Flower;
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
class FlowerDaoTest {
    @Autowired
    FlowerDao flowerDao;

    @Test
    @Rollback(false)
    @Order(1)
    public void testCreateFlower() {
        Flower flower = flowerDao.save(new Flower());
        assertThat(flower.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void testFindFlowerById() {
        Flower flower = flowerDao.findById((long) 86).orElse(null);
        assertThat(flower.getId()).isEqualTo((long) 86);
    }

    @Test
    @Order(3)
    public void testListFlower() {
        List<Flower> flowers = (List<Flower>) flowerDao.findAll();
        assertThat(flowers).size().isGreaterThan(0);
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void testUpdateFlower() {
        Flower flower = flowerDao.findById((long) 86).orElse(null);
        flower.setLevelOfFreshness(2);
        flower.setLengthOfPedicel(14.5);

        flowerDao.save(flower);

        Flower updatedFlower = flowerDao.findById((long) 86).orElse(null);

        assertThat(updatedFlower.getLevelOfFreshness()).isEqualTo(2);
        assertThat(updatedFlower.getLengthOfPedicel()).isEqualTo(14.5);
    }

    @Test
    @Rollback(false)
    @Order(8)
    public void testDeleteFlower() {
        Flower flower = flowerDao.findById((long) 86).orElse(null);

        flowerDao.deleteById(flower.getId());

        Flower deletedFlower = flowerDao.findById((long) 86).orElse(null);

        assertThat(deletedFlower).isNull();

    }
    @Test
    @Order(5)
    void findFlowersByBouquetID() {
        List<Flower> flowers = (List<Flower>) flowerDao.findFlowersByBouquetID((long)1);
        for(int i = 0; i < flowers.size(); i++){
            assertThat(flowers.get(i).getBouquet().getId()).isEqualTo(1);
        }

    }

    @Test
    @Order(6)
    void findFlowersByLength() {
        List<Flower> flowers = (List<Flower>) flowerDao.findFlowersByLength(7,9,1);
        for(int i = 0; i < flowers.size(); i++){
            assertThat(flowers.get(i).getLengthOfPedicel()).isGreaterThan(7);
            assertThat(flowers.get(i).getLengthOfPedicel()).isLessThan(9);
        }
    }

    @Test
    @Order(7)
    void sortFlowersByLevel() {
        List<Flower> flowers = (List<Flower>) flowerDao.sortFlowersByLevel(1);
        for(int i = 1; i < flowers.size(); i++){
            assertThat(flowers.get(i).getLevelOfFreshness()).isGreaterThan(flowers.get(i - 1).getLevelOfFreshness());
        }
    }
}