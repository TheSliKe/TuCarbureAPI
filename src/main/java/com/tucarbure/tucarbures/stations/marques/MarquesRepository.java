package com.tucarbure.tucarbures.stations.marques;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MarquesRepository extends CrudRepository<MarqueDB, String> {

    void deleteById(UUID uuid);

}
