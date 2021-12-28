package com.nulp.FlowerProject.controllers;

import com.nulp.FlowerProject.form.FlowerForm;
import com.nulp.FlowerProject.models.Bouquet;
import com.nulp.FlowerProject.models.FindFromData;
import com.nulp.FlowerProject.models.Flower;
import com.nulp.FlowerProject.service.BouquetService;
import com.nulp.FlowerProject.service.FlowerService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(FlowerController.class)
class FlowerControllerTest {

    @MockBean
    private FlowerService flowerService;

    @MockBean
    private BouquetService bouquetService;

    @MockBean
    private FindFromData findFromData;

    @Mock
    private FlowerForm flowerForm;

    @InjectMocks
    private FlowerController flowerController;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(this.flowerController).build();
    }


    @Test
    void index() throws Exception {
        Flower flower = new Flower();
        Bouquet bouquet = new Bouquet(1,"new", 20);
        flower.setBouquet(bouquet);
        List<Flower> flowers = new ArrayList<Flower>();
        flowers.add(flower);
        when(flowerService.findFlowersByBouquetID(1)).thenReturn(flowers);

        this.mockMvc.perform(get("/1/listFlowers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("flowers"))
                .andExpect(model().attributeExists("find"))
                .andExpect(view().name("listFlower"));
    }

    @Test
    void listOfSortedFlowers() throws Exception{
        Flower flower = new Flower();
        Bouquet bouquet = new Bouquet(1,"new", 20);
        flower.setBouquet(bouquet);
        List<Flower> flowers = new ArrayList<Flower>();
        flowers.add(flower);
        when(flowerService.sortFlowersByLevel(1)).thenReturn(flowers);

        this.mockMvc.perform(get("/1/listFlowers/sorted"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("flowers"))
                .andExpect(model().attributeExists("find"))
                .andExpect(view().name("listFlower"));
    }

    @Test
    void listOfFoundFlowers() throws Exception {
        Flower flower = new Flower(1,"Rose", 6,2,new Bouquet(1,"new", 20));
        List<Flower> flowers = new ArrayList<Flower>();
        flowers.add(flower);
        findFromData.setMaxLength(7);
        findFromData.setMinLength(5);
        when(flowerService.findFlowersByLength(findFromData.getMinLength(),findFromData.getMaxLength(),1)).thenReturn(flowers);

        this.mockMvc.perform(post("/1/listFlowers/found"))
                .andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception{
        Flower flower = new Flower();
        Bouquet bouquet = new Bouquet(1,"new", 20);
        flower.setBouquet(bouquet);
        when(flowerService.findById(1)).thenReturn(Optional.of(flower));
        this.mockMvc.perform(get("/editFlower/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editFlower"));
    }

    @Test
    void update() throws Exception{
        Flower flower = new Flower();
        Bouquet bouquet = new Bouquet(1,"new", 20);
        flower.setBouquet(bouquet);
        when(flowerService.findById(1)).thenReturn(Optional.of(flower));
        this.mockMvc.perform(post("/editedFlower/1"))
                .andExpect(status().is(302));
        verify(flowerService).save(flower);
    }

    @Test
    void deleteFlower() throws Exception{
        Flower flower = new Flower();
        Bouquet bouquet = new Bouquet(1,"new", 20);
        flower.setBouquet(bouquet);
        when(flowerService.findById(1)).thenReturn(Optional.of(flower));
        when(bouquetService.findById(flower.getBouquet().getId())).thenReturn(Optional.of(new Bouquet()));
        when(flowerService.returnPrice("Rose")).thenReturn(15);

        this.mockMvc.perform(get("/deleteFlower/1"))
                .andExpect(status().is(302));

        verify(flowerService).delete(any(Flower.class));
    }
}