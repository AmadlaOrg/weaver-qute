package org.amadla.weaver.qute;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;

@TopCommand
@Command(
        name = "weaver-qute",
        description = "Qute template engine plugin for Amadla Weaver",
        mixinStandardHelpOptions = true,
        version = "weaver-qute 1.0.0",
        subcommands = {
                InfoCommand.class,
                RenderCommand.class
        }
)
public class WeaverQuteCommand {
}
