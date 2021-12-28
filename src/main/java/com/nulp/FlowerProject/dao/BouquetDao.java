package com.nulp.FlowerProject.dao;

import com.nulp.FlowerProject.models.Bouquet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BouquetDao extends CrudRepository<Bouquet, Long> {
}
