package com.nulp.FlowerProject.service;

import com.nulp.FlowerProject.dao.BouquetDao;
import com.nulp.FlowerProject.dao.FlowerDao;
import com.nulp.FlowerProject.models.Bouquet;
import com.nulp.FlowerProject.models.Flower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FlowerServiceTest {

    @Mock
    FlowerDao flowerDao;

    @InjectMocks
    FlowerService flowerService;


    @Test
    void findAll() {
        List<Flower> flowers = new ArrayList<Flower>();
        Flower flowerOne = new Flower();
        Flower flowerTwo = new Flower();
        Flower flowerThree = new Flower();

        flowers.add(flowerOne);
        flowers.add(flowerTwo);
        flowers.add(flowerThree);

        when(flowerDao.findAll()).thenReturn(flowers);

        List<Flower> flowerList = (List<Flower>)flowerService.findAll();

        assertEquals(3, flowerList.size());
        verify(flowerDao, times(1)).findAll();
    }

    @Test
    void findFlowersByBouquetID() {
        List<Flower> flowers = new ArrayList<Flower>();
        when(flowerDao.findFlowersByBouquetID((long)1)).thenReturn(flowers);

        List<Flower> flowersList = (List<Flower>)flowerService.findFlowersByBouquetID((long)1);

        assertEquals(flowers, flowersList);
    }

    @Test
    void findFlowersByLength() {
        List<Flower> flowers = new ArrayList<Flower>();
        when(flowerDao.findFlowersByLength(10,12,(long)1)).thenReturn(flowers);

        List<Flower> flowersList = (List<Flower>)flowerService.findFlowersByLength(10,12,(long)1);

        assertEquals(flowers, flowersList);
    }

    @Test
    void sortFlowersByLevel() {
        List<Flower> flowers = new ArrayList<Flower>();
        when(flowerDao.sortFlowersByLevel((long)1)).thenReturn(flowers);

        List<Flower> flowersList = (List<Flower>)flowerService.sortFlowersByLevel((long)1);

        assertEquals(flowers, flowersList);
    }

    @Test
    void findById() {
        when(flowerDao.findById((long)1)).thenReturn(Optional.of(new Flower("Rose")));

        Flower flower = flowerService.findById((long)1).orElse(null);

        assertEquals("Rose", flower.getType());
    }

    @Test
    void save() {
        Flower createdFlower = new Flower();

        flowerService.save(createdFlower);
        verify(flowerDao, times(1)).save(createdFlower);
    }

    @Test
    void saveAll() {
        List<Flower> createdFlowers = new ArrayList<Flower>();

        flowerService.saveAll(createdFlowers);
        verify(flowerDao, times(1)).saveAll(createdFlowers);
    }

    @Test
    void delete() {
        Flower createdFlower = new Flower();

        flowerService.delete(createdFlower);
        verify(flowerDao, times(1)).delete(createdFlower);
    }

    @Test
    void returnPriceOne() {
        assertEquals(15, flowerService.returnPrice("Rose"));
    }

    @Test
    void returnPriceTwo() {
        assertEquals(10, flowerService.returnPrice("Tulip"));
    }

    @Test
    void returnPriceThree() {
        assertEquals(5, flowerService.returnPrice("Chamomile"));
    }
}