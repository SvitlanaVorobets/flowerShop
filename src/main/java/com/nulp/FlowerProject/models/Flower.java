package com.nulp.FlowerProject.models;

import javax.persistence.*;


@Entity
public class Flower{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    @Column(name = "lengthodpedicel")
    private double lengthOfPedicel;
    @Column(name = "leveloffreshness")
    private int levelOfFreshness;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "bouquetid", nullable = false)
    private Bouquet bouquet;

    public Flower(){}
    public Flower(String type){
        this.type = type;
    }

    public Flower(long id, String type, double lengthOfPedicel, int levelOfFreshness, Bouquet bouquet) {
        this.id = id;
        this.type = type;
        this.lengthOfPedicel = lengthOfPedicel;
        this.levelOfFreshness = levelOfFreshness;
        this.bouquet = bouquet;
    }

    public Bouquet getBouquet() {
        return bouquet;
    }

    public void setBouquet(Bouquet bouquet) {
        this.bouquet = bouquet;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLengthOfPedicel(double lengthOfPedicel) {
        this.lengthOfPedicel = lengthOfPedicel;
    }

    public void setLevelOfFreshness(int levelOfFreshness) {
        this.levelOfFreshness = levelOfFreshness;
    }

    public double getLengthOfPedicel() {
        return lengthOfPedicel;
    }

    public int getLevelOfFreshness() {
        return levelOfFreshness;
    }

    @Override
    public String toString(){
        return "\nFlower: " + type + "; length of pedicel: " + lengthOfPedicel +
                "; level of freshness: " + levelOfFreshness;
    }
}

