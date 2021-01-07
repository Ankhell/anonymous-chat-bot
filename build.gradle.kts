plugins {
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.2.0"
}

version = "0.1"
group = "anonymous.chat.bot"

repositories {
    mavenCentral()
    jcenter()
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("anonymous.chat.bot.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("info.picocli:picocli")
    implementation("io.micronaut.picocli:micronaut-picocli")
    implementation("javax.annotation:javax.annotation-api")
    testImplementation("io.micronaut:micronaut-http-client")

    implementation("org.telegram:telegrambots:5.0.1")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("ch.qos.logback:logback-core:1.2.3")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation ("com.google.code.gson:gson:2.8.6")

    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.sql:micronaut-hibernate-jpa")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    runtimeOnly("org.postgresql:postgresql:42.2.18")
    compileOnly("org.projectlombok:lombok:1.18.16")

    annotationProcessor ("org.projectlombok:lombok:1.18.16")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("info.picocli:picocli-codegen:4.2.0")
}


application {
    mainClass.set("me.ankhell.anonymous.chat.bot.AnonymousChatBotCommand")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
    targetCompatibility = JavaVersion.toVersion("11")
}



