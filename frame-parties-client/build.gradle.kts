import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api(project(":frame-parties-api"))

    api("org.springframework.cloud:spring-cloud-starter-openfeign")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
