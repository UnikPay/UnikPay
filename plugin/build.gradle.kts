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
    maven("https://repo.skriptlang.org/releases")
}

dependencies {
    implementation(project(":unikpay-api"))
    implementation("io.socket:socket.io-client:2.1.0") {
        exclude("org.json", "json")
        exclude("com.squareup.okhttp3", "okhttp")
    }
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")

    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT") {
        exclude("com.google.code.gson", "gson")
        exclude("com.google.guava", "guava")
        exclude("junit", "junit")
        exclude("org.yaml", "snakeyaml")
    }
    implementation("com.google.guava:guava:33.2.0-jre")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.json:json:20240303")
    compileOnly("com.github.SkriptLang:Skript:2.8.5") {
        exclude("com.sk89q", "worldguard")
        exclude("net.milkbowl.vault", "Vault")
    }
    implementation("org.spongepowered:configurate-yaml:4.2.0-SNAPSHOT")
    implementation("net.kyori:adventure-api:4.17.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.2")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
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
    exclude("META-INF/*")
    minimize()
    relocate("okhttp3", "dk.manaxi.okhttp3")
    relocate("okio", "dk.manaxi.okio")
    relocate("org.json", "dk.manaxi.json")
    relocate("com.google", "dk.manaxi.google")
    relocate("io.socket", "dk.manaxi.socket")
    relocate("javax.annotation", "dk.manaxi.annotation")
    relocate("kotlin", "dk.manaxi.kotlin")
    relocate("org.jetbrains.annotations", "dk.manaxi.annotations")
    relocate("org.checkerframework", "dk.manaxi.checkerframework")
    relocate("net.kyori", "dk.manaxi.kyori")
    archiveFileName.set("UnikPay.jar")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
