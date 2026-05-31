plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"
    application
}

repositories {
    mavenCentral()
}

dependencies {

    // ===== Testing =====
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // ===== Core utils =====
    implementation("com.google.guava:guava:33.0.0-jre")

    // ===== HTTP Client =====
    implementation("org.apache.httpcomponents.client5:httpclient5:5.2.1")

    // ===== Jackson (IMPORTANT FIXED SETUP) =====
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.17.0"))

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // ===== Kotlin stdlib =====
    implementation(kotlin("stdlib"))

    // ===== Lombok (only if you're still using Java classes) =====
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
}

application {
    mainClass.set("tunix.app.AppLauncher")
}

tasks.test {
    useJUnitPlatform()
}