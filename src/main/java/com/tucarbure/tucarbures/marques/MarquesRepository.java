package com.tucarbure.tucarbures.marques;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MarquesRepository extends CrudRepository<MarqueDB, String> {

    void deleteById(UUID uuid);
    Optional<MarqueDB> findById(UUID uuid);

    Optional<MarqueDB> findByNomAndDescription(String nom, String description);



}
