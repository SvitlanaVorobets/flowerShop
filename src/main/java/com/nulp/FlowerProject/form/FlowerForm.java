package com.nulp.FlowerProject.form;

import com.nulp.FlowerProject.models.Flower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowerForm {
    private List<Flower> flowers;
    Map<String, Integer> flowerMap = new HashMap<>();
    {
        flowerMap.put("Rose", 15);
        flowerMap.put("Tulip", 10);
        flowerMap.put("Chamomile", 5);
    }

    public FlowerForm() {
        flowers = new ArrayList<>();
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }

    public void addFlower(Flower flower) {
        this.flowers.add(flower);
    }

    public int returnPriceFromList(List<Flower> flowers){
        int price = 0;
        for(int i = 0; i < flowers.size(); i++){
            String type = flowers.get(i).getType();
            price += flowerMap.get(type);
        }
        return price;
    }
}
