package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class State {

    private final String uuid;
    private final String redirectUri;
}
