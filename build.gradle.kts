plugins {
    id("maven-publish")
    id("java")
}

group = "com.teamresourceful"
version = "1.1.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.1.0")

    implementation("io.netty:netty-buffer:4.1.97.Final")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    withSourcesJar()
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set("Byte Codecs")
                description.set("ByteCodecs isa library for encoding and decoding bytebufs in a hard defined way.")
                url.set("https://github.com/Team-Resourceful/ByteCodecs")

                licenses {
                    license {
                        name.set("MIT")
                    }
                }

                scm {
                    connection.set("git:https://github.com/Team-Resourceful/ByteCodecs.git")
                    developerConnection.set("git:https://github.com/Team-Resourceful/ByteCodecs.git")
                    url.set("https://github.com/Team-Resourceful/ByteCodecs")
                }
            }
        }
    }

    repositories {
        maven {
            setUrl("https://maven.resourcefulbees.com/repository/maven-releases/")

            credentials {
                username = System.getenv("MAVEN_USER")
                password = System.getenv("MAVEN_PASS")
            }
        }
    }
}