import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.vanniktech.maven.publish.AndroidSingleVariantLibrary

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.dokka.jetbrains)
}

tasks.dokkaHtml.configure {
    outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCS/ALL/HTML"))
}

tasks.dokkaJekyll.configure {
    outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCS/ALL/MARKDOWN"))
}

tasks.dokkaJavadoc.configure {
    outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCS/ALL/JAVADOC"))
}

mavenPublishing {
    coordinates("io.github.bodenberg", "appdimens-all", "2.0.0")

    configure(AndroidSingleVariantLibrary(
            publishJavadocJar = true,
            sourcesJar = true
        )
    )

    pom {
        name.set("AppDimens All-in-One: Complete Responsiveness Package")
        description.set("The most convenient package. It combines all AppDimens modules into a single dependency: Core Dynamic/Fixed (DY/FX), SDP, and SSP. Install this module for immediate access to all responsive dimensioning features for Jetpack Compose, XML Views, and Data Binding. " +
                "(android, kotlin, java, jetpack-compose, xml, swift, swiftui, ios, dp, sp, sdp, ssp, dimensions, responsive, layout, design-system, adaptive, dynamic, fixed, view-system)")
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
    namespace = "com.appdimens.all"
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
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)

    api(project(":appdimens_dynamic"))
    api(project(":appdimens_ssps"))
    api(project(":appdimens_sdps"))
    api(project(":appdimens_library"))

    dokkaPlugin(libs.android.documentation.plugin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}