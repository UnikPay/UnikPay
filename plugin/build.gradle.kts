import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.internal.impldep.org.apache.maven.model.Build

plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    //Skript
    maven("https://raw.githubusercontent.com/bensku/mvn-repo/master")
    maven("https://repo.codemc.org/repository/maven-public")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":unikpay-api"))
    compileOnly("org.jetbrains:annotations:24.0.0")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("ch.njol:skript:2.2-dev32") {
        exclude("com.sk89q", "worldguard")
        exclude("net.milkbowl.vault", "Vault")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}