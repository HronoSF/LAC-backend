package com.github.hronosf.services;

import com.github.hronosf.dto.State;

public interface StateService {

    String encodeState(String redirectUri);

    State decodeState(String state);
}
