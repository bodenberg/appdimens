import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.dokka.jetbrains)
}

tasks.dokkaHtml.configure {
    outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCS/DYNAMIC/HTML"))
}

tasks.dokkaJekyll.configure {
    outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCS/DYNAMIC/MARKDOWN"))
}

tasks.dokkaJavadoc.configure {
    outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCS/DYNAMIC/JAVADOC"))
}

mavenPublishing {
    coordinates("io.github.bodenberg", "appdimens-dynamic", "1.1.0")

    configure(
        AndroidSingleVariantLibrary(
            publishJavadocJar = true,
            sourcesJar = true
        )
    )

    pom {
        name.set("AppDimens Core: Dynamic (DY) and Fixed (FX) Dimensioning")
        description.set(
            "The essential AppDimens module for responsive scaling via code. It includes the Dynamic (DY) model for proportional dimensioning (ideal for containers and fluid layouts) and the refined Fixed (FX) model for logarithmic scaling (ideal for paddings, margins, and buttons). Supports Jetpack Compose, XML Views (via Data Binding), and direct calls in Kotlin/Java. " +
                    "(android, kotlin, java, jetpack-compose, xml, swift, swiftui, ios, dp, sp, sdp, ssp, dimensions, responsive, layout, design-system, adaptive, dynamic, fixed, view-system)"
        )
        url.set("https://github.com/bodenberg/appdimens")
        inceptionYear.set("2025")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("bodenberg")
                name.set("Jean Bodenberg")
                email.set("jc8752719@gmail.com")
            }
        }
        scm {
            connection.set("scm:git:github.com/bodenberg/appdimens.git")
            developerConnection.set("scm:git:ssh://github.com/bodenberg/appdimens.git")
            url.set("https://github.com/bodenberg/appdimens")
        }
    }
    val isJitPack = System.getenv("JITPACK") != null || System.getenv("CI") == "true"
    if (!isJitPack && (project.findProperty("signing.keyId") != null || project.findProperty("signing.secretKey") != null)) {
        signAllPublications()
        publishToMavenCentral()
    }
}

publishing {
    repositories {
        maven {
            name = "SonaType"
            url = uri("https://ossrh-staging-api.central.sonatype.com/service/local/")
            credentials {
                username = project.findProperty("mavenCentralUsername") as String?
                password = project.findProperty("mavenCentralPassword") as String?
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/bodenberg/appdimens")
            credentials {
                username = project.findProperty("gpr.user") as String?
                password = project.findProperty("gpr.key") as String?
            }
        }
    }
}

android {
    namespace = "com.appdimens.dynamic"
    compileSdk = 36

    defaultConfig {
        minSdk = 25
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.get()
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    api(project(":appdimens_library"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.core)
    dokkaPlugin(libs.android.documentation.plugin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}