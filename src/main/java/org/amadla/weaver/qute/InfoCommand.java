package org.amadla.weaver.qute;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine.Command;

import java.util.List;
import java.util.Map;

@Command(name = "info", description = "Show plugin metadata")
public class InfoCommand implements Runnable {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void run() {
        try {
            Map<String, Object> metadata = Map.of(
                    "name", "weaver-qute",
                    "version", "1.0.0",
                    "engine", "qute",
                    "supports", List.of("amadla.org/entity/template@^v1.0.0"),
                    "file_extensions", List.of(".qute", ".qute.html", ".qute.txt"),
                    "description", "Quarkus Qute template engine plugin for Weaver"
            );
            String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(metadata);
            System.out.println(json);
        } catch (Exception e) {
            System.err.println("Error encoding metadata: " + e.getMessage());
            System.exit(1);
        }
    }
}
