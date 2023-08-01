import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

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
    implementation("io.socket:socket.io-client:2.1.0") {
        exclude("org.json", "json")
    }
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")

    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT") {
        exclude("com.google.code.gson", "gson")
        exclude("com.google.guava", "guava")
        exclude("junit", "junit")
        exclude("org.yaml", "snakeyaml")
    }
    implementation("com.google.guava:guava:32.1.2-jre")
    implementation("com.google.code.gson:gson:2.10.1")
    compileOnly("com.github.SkriptLang:Skript:2.6.2") {
        exclude("com.sk89q", "worldguard")
        exclude("net.milkbowl.vault", "Vault")
    }
}

tasks.processResources {
    val placeholders = mapOf(
        "version" to project.rootProject.version
    )

    filesMatching("plugin.yml") {
        expand(placeholders)
    }
}

tasks.withType<ShadowJar> {
    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/*.RSA")
    exclude("META-INF/LICENSE")
    minimize()
    archiveFileName.set("UnikPay.jar")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}