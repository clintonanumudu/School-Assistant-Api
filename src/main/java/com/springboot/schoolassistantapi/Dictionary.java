package com.springboot.schoolassistantapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Dictionary {
    private Map<String, String> translations;

    public Dictionary() {
        translations = new HashMap<>();
    }

    public void addTranslation(String word, String translation) {
        translations.put(word, translation);
    }

    public String translate(String word) {
        return translations.getOrDefault(word, word); // Return the translation or the original word if not found.
    }
}
