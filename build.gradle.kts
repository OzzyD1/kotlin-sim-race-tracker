plugins {
    kotlin("jvm") version "2.0.20"
    // Plugin for Dokka - KDoc generating tool
    id("org.jetbrains.dokka") version "1.9.20"
    application
}

group = "ie.setu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // dependencies for logging
    // https://mvnrepository.com/artifact/io.github.oshai/kotlin-logging
    implementation("io.github.oshai:kotlin-logging:7.0.0")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.16")

    //For Streaming to XML and JSON
    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream:xstream:1.4.21")
    // https://mvnrepository.com/artifact/org.codehaus.jettison/jettison
    implementation("org.codehaus.jettison:jettison:1.5.4")

    // For generating a Dokka Site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(16)
}