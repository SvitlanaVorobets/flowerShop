package com.nulp.FlowerProject.service;

import com.nulp.FlowerProject.dao.FlowerDao;
import com.nulp.FlowerProject.models.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FlowerService {
    @Autowired
    private FlowerDao flowerDao;
    Map<String, Integer> flowerMap = new HashMap<>();
    {
        flowerMap.put("Rose", 15);
        flowerMap.put("Tulip", 10);
        flowerMap.put("Chamomile", 5);
    }

    public Iterable<Flower> findAll() {
        return flowerDao.findAll();
    }

    public Iterable<Flower> findFlowersByBouquetID(long id){
        return flowerDao.findFlowersByBouquetID(id);
    }

    public Iterable<Flower> findFlowersByLength(double minLength, double maxLength, long id){
        return flowerDao.findFlowersByLength(minLength, maxLength, id);
    }

    public Iterable<Flower> sortFlowersByLevel(long id){
        return flowerDao.sortFlowersByLevel(id);
    }

    public Optional<Flower> findById(long id) {
        return flowerDao.findById(id);
    }

    public void save(Flower flower) {
        flowerDao.save(flower);
    }

    public void saveAll(List<Flower> flowers){
        flowerDao.saveAll(flowers);
    }

    public void delete(Flower flower) {
        flowerDao.delete(flower);
    }

    public int returnPrice(String nameOFBouquet){
        return flowerMap.get(nameOFBouquet);
    }
}
