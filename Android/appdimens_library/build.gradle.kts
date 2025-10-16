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
    outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCS/LIBRARY/HTML"))
}

tasks.dokkaGfm.configure {
    outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCS/LIBRARY/MARKDOWN"))
}

tasks.dokkaJavadoc.configure {
    outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCS/LIBRARY/JAVADOC"))
}

mavenPublishing {
    coordinates("io.github.bodenberg", "appdimens-library", "1.0.4")

    configure(
        AndroidSingleVariantLibrary(
            publishJavadocJar = true,
            sourcesJar = true
        )
    )

    pom {
        name.set("AppDimens: Cross-Platform UI Responsiveness (SDP/SSP, Dynamic/Fixed)")
        description.set(
            "The complete dynamic and responsive dimensioning library for Android (Jetpack Compose, XML Views, Kotlin, Java) and iOS (Swift, SwiftUI). It uses mathematical models (SDP, SSP, Dynamic, and Fixed) to ensure your UI adapts perfectly to any screen (phones, tablets, foldables, TVs, cars, and wearables), solving the problem of adaptive layout precisely and efficiently. " +
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
                email.set("jean.bodenberg@gmail.com")
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
    namespace = "com.appdimens.library"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
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
    implementation(libs.androidx.annotation.jvm)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    dokkaPlugin(libs.android.documentation.plugin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}