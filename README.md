<br/>
<h1 align="center">spring-componentmap</h1>

[![Awesome Kotlin](https://img.shields.io/badge/awesome-kotlin-orange?logo=awesomelists)](https://kotlin.link/)
![Maven Central](https://img.shields.io/maven-central/v/dev.krud/spring-componentmap)
[![CircleCI](https://img.shields.io/circleci/build/github/krud-dev/spring-componentmap/master)](https://circleci.com/gh/krud-dev/spring-componentmap/tree/master)
[![Codecov](https://img.shields.io/codecov/c/gh/krud-dev/spring-componentmap?token=1EG9H9RK5Q)](https://codecov.io/gh/krud-dev/spring-componentmap)
[![GitHub](https://img.shields.io/github/license/krud-dev/spring-componentmap)](https://github.com/krud-dev/spring-componentmap/blob/master/LICENSE)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg)](https://github.com/krud-dev/spring-componentmap/issues)

- [Overview](#overview)
- [Documentation](#documentation)
- [Installation](#installation)
  - [Requirements](#requirements)
  - [Maven](#maven)
  - [Gradle](#gradle)
    - [Groovy DSL](#groovy-dsl)
    - [Kotlin DSL](#kotlin-dsl)
- [Contributing](#contributing)
- [License](#license)

## Overview

spring-componentmap is a JVM library that provides a simple way to inject a map of beans by type.

The library allows both the injection of a map of beans by type and the injection of a map of lists of beans by type.

## Example

Head to the [Examples directory](spring-componentmap/src/test/kotlin/dev/krud/spring/componentmap/examples) to see usage examples.

## Installation
### Requirements

* Minimum supported Java version: 1.8
* Spring Boot 2.5.0+

### Maven
```xml
<dependency>
  <groupId>dev.krud</groupId>
  <artifactId>spring-componentmap</artifactId>
  <version>0.1.0</version>
</dependency>
```

### Gradle
#### Groovy DSL
```groovy
implementation 'dev.krud:spring-componentmap:0.1.0'
```
#### Kotlin DSL
```kotlin
implementation("dev.krud:spring-componentmap:0.1.0")
```

### Setup

Simply add the dependency to your Spring Boot project, Spring Boot will then run the `ComponentMapAutoConfiguration` class and register the `ComponentMapBeanPostProcessor` bean.

### Usage

To use the `@ComponentMap` annotation, simply add it to a `Map` field and Spring Boot will automatically populate it with all beans of the specified type.

#### Kotlin Example

```kotlin
interface ActionHandler {
    /**
     * The action that this handler can handle, add the `@ComponentMapKey` annotation to the getter in order to register it
     */
    @get:ComponentMapKey
    val type: String
    fun handle()
}

class ActionHandler1 : ActionHandler {
    override val type = "type1"
    override fun handle() {
        println("ActionHandler1")
    }
}

class ActionHandler2 : ActionHandler {
    override val type = "type2"
    override fun handle() {
        println("ActionHandler2")
    }
}

@Component
class ActionHandlerMap {
    /**
     * The `@ComponentMap` annotation will automatically populate this map with all beans of type `ActionHandler`
     */
    @ComponentMap private lateinit var handlers: Map<String, ActionHandler>
    
    fun handle(type: String) {
        handlers[type]?.handle()
    }
}
```

#### Java Example

```java
@Component
public class ActionHandlerMap {
    /**
     * The `@ComponentMap` annotation will automatically populate this map with all beans of type `ActionHandler`
     */
    @ComponentMap private Map<String, ActionHandler> handlers;
    
    public void handle(String type) {
        handlers.get(type).handle();
    }
}

public interface ActionHandler {
    /**
     * The action that this handler can handle, add the `@ComponentMapKey` annotation to the getter in order to register it
     */
    @ComponentMapKey
    String getType();
    void handle();
}

@Component
public class ActionHandler1 implements ActionHandler {
    @Override
    public String getType() {
        return "type1";
    }

    @Override
    public void handle() {
        System.out.println("ActionHandler1");
    }
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change. See [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

## License
spring-componentmap is licensed under the [MIT](https://choosealicense.com/licenses/mit/) license. For more information, please see the [LICENSE](LICENSE) file.
