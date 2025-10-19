plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.jpa") version "1.9.25"
  kotlin("plugin.allopen") version "1.9.25"
  id("io.spring.dependency-management") version "1.1.7"
  kotlin("plugin.spring") version "1.9.25"
}

group = "quickswap"
version = "0.0.1-SNAPSHOT"
description = "commons"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.6")
  }
}

dependencies {
  api("jakarta.persistence:jakarta.persistence-api")
  api("org.springframework.security:spring-security-web")
  api("org.springframework.security:spring-security-config")
  api("jakarta.servlet:jakarta.servlet-api")
  api("io.jsonwebtoken:jjwt-api:0.13.0")
  api("io.jsonwebtoken:jjwt-impl:0.13.0")
  api("io.jsonwebtoken:jjwt-jackson:0.13.0")

  api("com.fasterxml.jackson.module:jackson-module-kotlin")
  api("org.jetbrains.kotlin:kotlin-reflect")

  api("com.github.f4b6a3:ulid-creator:5.2.3")

  api("org.slf4j:slf4j-api")

  implementation("org.springframework.boot:spring-boot-autoconfigure")

  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("io.mockk:mockk:1.14.6")
  testImplementation("org.springframework.security:spring-security-test")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testImplementation(kotlin("test"))
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
  useJUnitPlatform()
}