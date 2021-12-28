package com.nulp.FlowerProject.dao;

import com.nulp.FlowerProject.models.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerDao extends JpaRepository<Flower, Long> {
    @Query("select f from Flower f where f.bouquet.id = :id")
    Iterable<Flower> findFlowersByBouquetID(@Param("id") Long id);

    @Query("select f from Flower f where f.lengthOfPedicel > :minLength and f.lengthOfPedicel < :maxLength and f.bouquet.id = :id")
    Iterable<Flower> findFlowersByLength(@Param("minLength") double minLength, @Param("maxLength") double maxLength, @Param("id") long id);

    @Query("select f from Flower f where f.bouquet.id = :id order by f.levelOfFreshness")
    Iterable<Flower> sortFlowersByLevel(@Param("id") long id);
}
