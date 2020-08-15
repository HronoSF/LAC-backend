package com.github.hronosf.services;

import java.util.Map;

public interface DocumentGenerationService {

    String generatePretrialAppeal(Map<String, String> mappings);

    String generatePostInventory(Map<String, String> mappings);
}
