package org.amadla.weaver.qute;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Renders Qute templates with provided data.
 */
@ApplicationScoped
public class TemplateRenderer {

    @Inject
    Engine engine;

    public String render(String templatePath, Map<String, Object> data) throws IOException {
        String templateContent = Files.readString(Path.of(templatePath));

        Template template = engine.parse(templateContent);

        return template.data(data).render();
    }
}
