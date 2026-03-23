# weaver-qute

Amadla Weaver plugin for the Quarkus Qute template engine, compiled as a native binary with GraalVM.

## Build

```bash
# JVM jar
make build

# Native binary (requires GraalVM with native-image)
make build-native

# Run tests
make test
```

## Usage

```bash
# Show plugin info
weaver-qute info

# Render a template with JSON input from stdin
echo '{"name": "nginx"}' | weaver-qute render -t config.qute

# Render with YAML file input
weaver-qute render -t config.qute -f data.yaml

# Render to output file
weaver-qute render -t config.qute -f data.yaml -o output.conf
```

## Qute Syntax

```
{name}                           - Variable
{server.host}                    - Nested access
{#for item in items}...{/for}    - Loop
{#if enabled}...{#else}...{/if}  - Conditional
```

See [Qute Reference](https://quarkus.io/guides/qute-reference) for full syntax.

## License

Copyright (c) Amadla. All rights reserved.
