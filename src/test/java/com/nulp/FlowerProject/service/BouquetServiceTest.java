package com.nulp.FlowerProject.service;

import com.nulp.FlowerProject.dao.BouquetDao;
import com.nulp.FlowerProject.models.Bouquet;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BouquetServiceTest {

    @Mock
    BouquetDao bouquetDao;

    @InjectMocks
    BouquetService bouquetService;

    @BeforeEach
    void setUp() {
        bouquetService = new BouquetService(bouquetDao);
    }

    @Test
    void findAll() {
        List<Bouquet> bouquets = new ArrayList<Bouquet>();
        Bouquet bouquetOne = new Bouquet("Bouquet1");
        Bouquet bouquetTwo = new Bouquet("Bouquet2");
        Bouquet bouquetThree = new Bouquet("Bouquet3");

        bouquets.add(bouquetOne);
        bouquets.add(bouquetTwo);
        bouquets.add(bouquetThree);

        when(bouquetDao.findAll()).thenReturn(bouquets);

        List<Bouquet> bouquetList = (List<Bouquet>)bouquetService.findAll();

        assertEquals(3, bouquetList.size());
        verify(bouquetDao, times(1)).findAll();
    }

    @Test
    void findById() {
        when(bouquetDao.findById((long)1)).thenReturn(Optional.of(new Bouquet("newBouquet")));

        Bouquet bouquet = bouquetService.findById((long)1).orElse(null);

        assertEquals("newBouquet", bouquet.getName());
    }

    @Test
    void save() {
        Bouquet createdBouquet = new Bouquet("newBouquet");

        bouquetService.save(createdBouquet);
        verify(bouquetDao, times(1)).save(createdBouquet);
    }

    @Test
    void delete() {
        Bouquet createdBouquet = new Bouquet("newBouquet");

        bouquetService.delete(createdBouquet);
        verify(bouquetDao, times(1)).delete(createdBouquet);
    }

    @Test
    void returnPriceOne() {
        assertEquals(5, bouquetService.returnPrice("Paper"));
    }

    @Test
    void returnPriceTwo() {
        assertEquals(10, bouquetService.returnPrice("Bow"));
    }

    @Test
    void returnPriceThree() {
        assertEquals(20, bouquetService.returnPrice("Beard"));
    }
}