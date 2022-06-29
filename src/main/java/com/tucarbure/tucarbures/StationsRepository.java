package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.marques.Marque;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StationsRepository extends CrudRepository<StationDB, String> {

    void deleteById(UUID uuid);
    Iterable<StationDB> findAll(Pageable pageable);

    Iterable<StationDB> findAllByAdresse_LatitudeBetweenAndAdresse_LongitudeBetween(double adresse_latitude, double adresse_latitude2, double adresse_longitude, double adresse_longitude2);
    Optional<StationDB> findById(UUID uuid);
    Iterable<StationDB> findAllByMarque(Marque marque);

    StationDB findByMarque_Nom(String nom);
}
