package org.amadla.weaver.qute;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Command(name = "render", description = "Render a Qute template")
public class RenderCommand implements Runnable {

    @Option(names = {"-t", "--template"}, description = "Path to the Qute template file", required = true)
    String templatePath;

    @Option(names = {"-f", "--file"}, description = "Input data file path (- for stdin)", defaultValue = "-")
    String filePath;

    @Option(names = {"-o", "--output"}, description = "Output file path (default: stdout)")
    String outputPath;

    @Inject
    InputLoader inputLoader;

    @Inject
    TemplateRenderer renderer;

    @Override
    public void run() {
        try {
            // Load input data
            Map<String, Object> data = inputLoader.load(filePath);

            // Render template
            String result = renderer.render(templatePath, data);

            // Write output
            if (outputPath != null) {
                Files.writeString(Path.of(outputPath), result);
            } else {
                System.out.print(result);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
