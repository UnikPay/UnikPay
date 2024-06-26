group = "dk.manaxi.unikpay"
version = "1.1.3"

repositories {
    mavenCentral()
}

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
}

subprojects {
    plugins.apply("java")
    plugins.apply("maven-publish")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(components["java"])
            }
        }
    }

}
