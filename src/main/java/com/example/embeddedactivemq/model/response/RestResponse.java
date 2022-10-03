package com.example.embeddedactivemq.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResponse {

    private Object data;
    private String message;
    private Boolean result;

}
