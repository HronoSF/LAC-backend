package com.github.hronosf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@AllArgsConstructor
public enum Constants {
    PATH(Paths.get(System.getProperty("user.home") + "/Desktop/generatedDocuments"));

    private final Path value;
}
