package com.tucarbure.tucarbures.releves;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HistoriqueReleveCarburantsRepository extends CrudRepository<HistoriqueReleveCarburants, String> {

    Iterable<HistoriqueReleveCarburants> findAllByStationIdOrderByDateDesc(UUID stationId);

    Optional<HistoriqueReleveCarburants> findById(UUID uuid);
}
