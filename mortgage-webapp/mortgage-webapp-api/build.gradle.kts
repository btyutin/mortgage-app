plugins {
    kotlin("plugin.spring")
}

dependencies {
    implementation(kotlin("stdlib"))

    api(project(":mortgage-domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}
