package com.tucarbure.tucarbures.releves.code;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodeCarburants {

    private String nom;
    private String code;

    public static CodeCarburantsBuilder codeCarburantsBuilder(){
        return CodeCarburants.builder();
    }
}
