package com.github.hronosf.services.impl;

import com.github.hronosf.dto.State;
import com.github.hronosf.services.StateService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StateServiceImpl implements StateService {

    private static final String DELIMITER = ".";

    @Override
    public String encodeState(String redirectUri) {
        String uuid = UUID.randomUUID().toString();
        State state = new State(uuid, redirectUri);
        return stateToString(state);
    }

    @Override
    public State decodeState(String state) {
        String[] parts = validateState(state);
        return new State(parts[0], parts[1]);
    }

    private String stateToString(State state) {
        return Stream.of(state.getUuid(), state.getRedirectUri())
                .map(String::getBytes)
                .map(Base64.getEncoder()::encodeToString)
                .collect(Collectors.joining(DELIMITER));
    }

    private String[] validateState(String state) {
        String[] encodedParts = state.split("\\" + DELIMITER);

        if (encodedParts.length != 2) {
            throw new RuntimeException("");
        }

        return Arrays.stream(encodedParts)
                .map(Base64.getDecoder()::decode)
                .map(String::new)
                .toArray(String[]::new);
    }
}
