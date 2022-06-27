package com.tucarbure.tucarbures.releves;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelevesController {

    @GetMapping("/releves")
    String getStations() {
        return "releves";
    }


}
