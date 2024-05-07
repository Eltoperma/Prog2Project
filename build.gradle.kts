plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("lib/jaco-mp3-player-0.9.3.jar"))
}

tasks.test {
    useJUnitPlatform()
}