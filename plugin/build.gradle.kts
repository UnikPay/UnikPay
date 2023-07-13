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
    maven("'https://repo.skriptlang.org/releases'")
}

dependencies {
    implementation(project(":unikpay-api"))
    implementation("io.socket:socket.io-client:2.1.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")

    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.github.SkriptLang:Skript:2.6.2") {
        exclude("com.sk89q", "worldguard")
        exclude("net.milkbowl.vault", "Vault")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}