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
    implementation("org.apache.logging.log4j:log4j-core:2.22.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.21")

    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT") {
        exclude("com.google.code.gson", "gson")
        exclude("com.google.guava", "guava")
        exclude("junit", "junit")
        exclude("org.yaml", "snakeyaml")
    }
    implementation("com.google.guava:guava:33.0.0-jre")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.json:json:20231013")
    compileOnly("com.github.SkriptLang:Skript:2.7.3") {
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
    relocate("okhttp3", "dk.manaxi.okhttp3")
    archiveFileName.set("UnikPay.jar")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
