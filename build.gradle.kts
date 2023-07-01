group = "dk.manaxi.unikpay"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

subprojects {
    plugins.apply("java")
}