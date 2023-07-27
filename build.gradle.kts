plugins {
    id("java")
}

group = "tech.thatgravyboat"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("io.netty:netty-buffer:4.1.82.Final")
}

tasks.test {
    useJUnitPlatform()
}