package com.nulp.FlowerProject.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.verify;

import com.nulp.FlowerProject.models.Bouquet;
import com.nulp.FlowerProject.service.BouquetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;


@WebMvcTest(BouquetController.class)
class BouquetControllerTest {

    @MockBean
    private BouquetService bouquetService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void index() throws Exception{
        when(bouquetService.findAll()).thenReturn(new ArrayList<Bouquet>());

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bouquets"))
                .andExpect(view().name("index"));
    }

    @Test
    void show() throws Exception{
        when(bouquetService.findById((long)1)).thenReturn(Optional.of(new Bouquet("newBouquet")));

        this.mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bouquet"))
                .andExpect(view().name("show"));
    }

    @Test
    void newBouquet() throws Exception{
        this.mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bouquet"))
                .andExpect(model().attributeExists("find"))
                .andExpect(view().name("create"));
    }

    @Test
    void create() throws Exception{
        this.mockMvc.perform(post("/created/1"))
                .andExpect(status().is(302));
        verify(bouquetService).save(any(Bouquet.class));
    }

    @Test
    void edit() throws Exception{
        when(bouquetService.findById((long)1)).thenReturn(Optional.of(new Bouquet("newBouquet")));
        this.mockMvc.perform(get("/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bouquet"))
                .andExpect(view().name("edit"));
    }

    @Test
    void update() throws Exception{
        this.mockMvc.perform(post("/updated/1"))
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("bouquet"));
    }

    @Test
    void deleteBouquet() throws Exception{
        when(bouquetService.findById((long)1)).thenReturn(Optional.of(new Bouquet("newBouquet")));

        this.mockMvc.perform(get("/deleted/1"))
                .andExpect(status().is(302));

        verify(bouquetService).delete(any(Bouquet.class));
    }
}