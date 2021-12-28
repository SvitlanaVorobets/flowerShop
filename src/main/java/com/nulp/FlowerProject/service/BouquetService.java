package com.nulp.FlowerProject.service;

import com.nulp.FlowerProject.dao.BouquetDao;
import com.nulp.FlowerProject.models.Bouquet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BouquetService {

    @Autowired
    private BouquetDao bouquetDao;
    Map<String, Integer> accessoryMap = new HashMap<>();
    {
        accessoryMap.put("Paper", 5);
        accessoryMap.put("Bow", 10);
        accessoryMap.put("Beard", 20);
    }

    public BouquetService(BouquetDao bouquetDao){
        this.bouquetDao = bouquetDao;
    }
    public Iterable<Bouquet> findAll() {
        return bouquetDao.findAll();
    }

    public Optional<Bouquet> findById(long id) {
        return bouquetDao.findById(id);
    }

    public void save(Bouquet bouquet) {
        bouquetDao.save(bouquet);
    }

    public void delete(Bouquet bouquet) {
        bouquetDao.delete(bouquet);
    }

    public int returnPrice(String accessory){
        return accessoryMap.get(accessory);
    }
}
