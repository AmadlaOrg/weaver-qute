package org.amadla.weaver.qute;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TemplateRendererTest {

    @Inject
    TemplateRenderer renderer;

    @Test
    void renderWithSimpleData() throws IOException {
        Path tmpl = Files.createTempFile("test", ".qute");
        try {
            Files.writeString(tmpl, "Hello {name}, port {port}");
            String result = renderer.render(tmpl.toString(), Map.of("name", "nginx", "port", 8080));
            assertEquals("Hello nginx, port 8080", result);
        } finally {
            Files.deleteIfExists(tmpl);
        }
    }

    @Test
    void renderWithLoop() throws IOException {
        Path tmpl = Files.createTempFile("test", ".qute");
        try {
            Files.writeString(tmpl, "{#for item in items}{item.key}: {item.value}\n{/for}");
            Map<String, Object> data = Map.of("items", List.of(
                    Map.of("key", "K1", "value", "V1"),
                    Map.of("key", "K2", "value", "V2")
            ));
            String result = renderer.render(tmpl.toString(), data);
            assertEquals("K1: V1\nK2: V2\n", result);
        } finally {
            Files.deleteIfExists(tmpl);
        }
    }

    @Test
    void renderWithConditional() throws IOException {
        Path tmpl = Files.createTempFile("test", ".qute");
        try {
            Files.writeString(tmpl, "{#if enabled}ON{#else}OFF{/if}");
            assertEquals("ON", renderer.render(tmpl.toString(), Map.of("enabled", true)));
            assertEquals("OFF", renderer.render(tmpl.toString(), Map.of("enabled", false)));
        } finally {
            Files.deleteIfExists(tmpl);
        }
    }

    @Test
    void renderNestedData() throws IOException {
        Path tmpl = Files.createTempFile("test", ".qute");
        try {
            Files.writeString(tmpl, "{server.host}:{server.port}");
            Map<String, Object> data = Map.of("server", Map.of("host", "localhost", "port", 3000));
            String result = renderer.render(tmpl.toString(), data);
            assertEquals("localhost:3000", result);
        } finally {
            Files.deleteIfExists(tmpl);
        }
    }
}
