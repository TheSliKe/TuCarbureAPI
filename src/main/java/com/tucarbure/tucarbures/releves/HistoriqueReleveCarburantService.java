package com.tucarbure.tucarbures.releves;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HistoriqueReleveCarburantService {

    @Autowired
    private HistoriqueReleveCarburantsRepository historiqueReleveCarburantsRepository;

    List<HistoriqueReleveCarburants> getHistoriqueReleveCarburants(UUID stationsId, int nb){

        List<HistoriqueReleveCarburants> historiqueReleveCarburantsList = new ArrayList<>();

        historiqueReleveCarburantsRepository.findAllByStationIdOrderByDateDesc(stationsId)
                .forEach(historiqueReleveCarburantsList::add);

        if (historiqueReleveCarburantsList.size() <= nb){
            return historiqueReleveCarburantsList;
        } else {

            List<HistoriqueReleveCarburants> temp = new ArrayList<>();

            for (int i = 0; i < nb; i++) {
                temp.add(historiqueReleveCarburantsList.get(i));
            }

            return temp;
        }
    }

    public HistoriqueReleveCarburants getHistorique(UUID historiqueId) {
        return historiqueReleveCarburantsRepository.findById(historiqueId).get();
    }
}
