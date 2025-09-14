// build.gradle.kts for core_farm microservice
// Justification: Using Gradle with Kotlin DSL for better type safety and IDE support.
// This setup follows Spring Boot 3.3 conventions for modular, scalable backend services.

plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
}

group = "com.avicola"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot starters for web, data JPA, security
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Database: H2 for demo (change to PostgreSQL for production)
    runtimeOnly("com.h2database:h2")
    // runtimeOnly("org.postgresql:postgresql")

    // Caching: Redis for performance optimization
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // JWT for security
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Kotlin standard library
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.h2database:h2") // For in-memory testing
}

tasks.withType<Test> {
    useJUnitPlatform()
}