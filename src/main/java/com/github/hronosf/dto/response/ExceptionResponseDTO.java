package com.github.hronosf.dto.response;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDTO {

    private Map<String, String> errors;
}
