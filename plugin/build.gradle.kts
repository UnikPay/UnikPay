
plugins {
    id("java-library")
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    //Skript
    maven("https://raw.githubusercontent.com/bensku/mvn-repo/master")

}

dependencies {
    implementation(project(":unikpay-api"))
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("ch.njol:skript:2.2-dev32")
}
