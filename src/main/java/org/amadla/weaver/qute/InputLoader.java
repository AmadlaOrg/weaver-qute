package org.amadla.weaver.qute;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Loads and parses input data from file or stdin.
 * Auto-detects JSON vs YAML.
 */
@ApplicationScoped
public class InputLoader {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public Map<String, Object> load(String filePath) throws IOException {
        String raw;
        if ("-".equals(filePath)) {
            raw = new String(System.in.readAllBytes());
        } else {
            raw = Files.readString(Path.of(filePath));
        }

        if (raw.isBlank()) {
            return Map.of();
        }

        return parse(raw);
    }

    public Map<String, Object> parse(String raw) throws IOException {
        String trimmed = raw.strip();

        // Try JSON first if starts with {
        if (trimmed.startsWith("{")) {
            try {
                return JSON_MAPPER.readValue(raw, new TypeReference<>() {});
            } catch (IOException ignored) {
                // fall through to YAML
            }
        }

        // Try YAML
        try {
            Map<String, Object> result = YAML_MAPPER.readValue(raw, new TypeReference<>() {});
            if (result != null) {
                return result;
            }
        } catch (IOException ignored) {
            // fall through
        }

        // Fallback JSON
        try {
            return JSON_MAPPER.readValue(raw, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IOException("Input is neither valid JSON nor YAML", e);
        }
    }
}
