package com.catering_app.Catering_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto {

    private boolean status;
    private String message;
}
