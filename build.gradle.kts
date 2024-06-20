import org.apache.tools.ant.taskdefs.Manifest

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation("org.junit:junit-bom:5.9.1");
//    testImplementation("org.junit.jupiter:junit-jupiter");

    implementation(files("lib/jaco-mp3-player-0.9.3.jar"));
    implementation("com.google.code.gson:gson:2.8.6");
}
tasks.test {
    useJUnitPlatform()
}

