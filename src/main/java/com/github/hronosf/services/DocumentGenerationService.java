package com.github.hronosf.services;

import java.io.FileInputStream;
import java.util.Map;

public interface DocumentGenerationService {

    FileInputStream generatePretrialDocument(Map<String, String> dataToFill);
}
