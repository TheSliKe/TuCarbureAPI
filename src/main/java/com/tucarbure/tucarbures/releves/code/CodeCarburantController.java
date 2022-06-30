package com.tucarbure.tucarbures.releves.code;

import com.tucarbure.tucarbures.response.StationsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.tucarbure.tucarbures.releves.code.CodeCarburants.codeCarburantsBuilder;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CodeCarburantController {

    @Autowired
    private CodeCarburantRepository codeCarburantRepository;

    @GetMapping("/carburants")
    @Operation(summary = "Liste des carburants", description = "Lors de l'appel, on retourne un JSON de la liste des carburants", responses = {
            @ApiResponse(responseCode = "200", description = "JSON ok")
    })
    List<CodeCarburants> getcarburants() {

        List<CodeCarburants> res = new ArrayList<>();

        for (CodeCarburantsDB ccdb : codeCarburantRepository.findAll()) {
            res.add(codeCarburantsBuilder().nom(ccdb.getNom()).code(ccdb.getCode()).build());
        }

        return res;
    }


}