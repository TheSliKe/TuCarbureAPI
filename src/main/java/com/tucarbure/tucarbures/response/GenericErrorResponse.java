package com.tucarbure.tucarbures.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericErrorResponse {

    private int code;
    private String message;

    public static GenericErrorResponseBuilder genericErrorResponseBuilder(){
        return GenericErrorResponse.builder();
    }

}
