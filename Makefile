JAVA_HOME ?= /home/jn/.local/graalvm
GRAALVM_HOME ?= $(JAVA_HOME)
export JAVA_HOME
export GRAALVM_HOME
export PATH := $(JAVA_HOME)/bin:$(PATH)

.PHONY: build
build: ## Build JVM jar
	@echo "---> Building JVM jar"
	@mvn package -DskipTests -q

.PHONY: build-native
build-native: ## Build native image with GraalVM
	@echo "---> Building native image"
	@mvn package -Dnative -DskipTests

.PHONY: test
test: ## Run tests
	@mvn test

.PHONY: test-native
test-native: ## Run native integration tests
	@mvn verify -Dnative

.PHONY: lint
lint: ## Check code style
	@mvn compile -q

.PHONY: clean
clean: ## Clean build artifacts
	@mvn clean -q

.PHONY: help
help: ## Help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
