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
    Optional<StationDB> findById(UUID uuid);
    Iterable<StationDB> findAllByMarque(Marque marque);
}
