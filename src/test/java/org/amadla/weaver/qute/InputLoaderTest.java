package org.amadla.weaver.qute;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class InputLoaderTest {

    @Inject
    InputLoader inputLoader;

    @Test
    void parseJsonObject() throws IOException {
        Map<String, Object> result = inputLoader.parse("{\"key\": \"value\"}");
        assertEquals("value", result.get("key"));
    }

    @Test
    void parseYamlObject() throws IOException {
        Map<String, Object> result = inputLoader.parse("key: value\nother: data");
        assertEquals("value", result.get("key"));
        assertEquals("data", result.get("other"));
    }

    @Test
    void parseEmptyJson() throws IOException {
        Map<String, Object> result = inputLoader.parse("{}");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void loadFromFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".json");
        try {
            Files.writeString(tempFile, "{\"name\": \"test\"}");
            Map<String, Object> result = inputLoader.load(tempFile.toString());
            assertEquals("test", result.get("name"));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void loadFromYamlFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".yaml");
        try {
            Files.writeString(tempFile, "name: test\nport: 8080");
            Map<String, Object> result = inputLoader.load(tempFile.toString());
            assertEquals("test", result.get("name"));
            assertEquals(8080, result.get("port"));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void loadEmptyFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".json");
        try {
            Files.writeString(tempFile, "");
            Map<String, Object> result = inputLoader.load(tempFile.toString());
            assertNotNull(result);
            assertTrue(result.isEmpty());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
