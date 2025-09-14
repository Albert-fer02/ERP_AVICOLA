// CoreFarmApplication.kt
// Justification: Main Spring Boot application class.
// Uses @SpringBootApplication for auto-configuration, enabling JPA, web, security.
// @EnableCaching enables Redis caching for performance.
// This is the entry point for the core_farm microservice, promoting modularity.

package com.avicola.corefarm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class CoreFarmApplication

fun main(args: Array<String>) {
    runApplication<CoreFarmApplication>(*args)
}