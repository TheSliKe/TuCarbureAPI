package com.tucarbure.tucarbures.stations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StationsRepository extends CrudRepository<StationDB, String> {

    void deleteById(UUID uuid);
}
