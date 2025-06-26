import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.com.intellij.openapi.util.text.HtmlChunk.icon
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.UUID

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets.all {
        languageSettings.enableLanguageFeature("ExplicitBackingFields")
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.accompanist.permissions)
            //di
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            //adaptive navigation
            implementation(libs.navigation.compose)
            implementation(libs.navigation.common)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(libs.material3.adaptive)
            implementation(libs.material3.adaptive.layout)
            implementation(libs.material3.adaptive.navigation)
            implementation(libs.material3.window.size)

            //multi languages
            implementation(libs.compose.remember.setting)

            //icons
            implementation(libs.material.icons.extended)

            //files
            implementation(libs.filekit.core)
            implementation(libs.filekit.dialogs)
            implementation(libs.filekit.dialogs.compose)

            //di
            implementation(libs.bundles.koin.common)

            //image
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)

            //network
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            //network
            implementation(libs.ktor.client.okhttp)

            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "ru.normno.rutubedownloader"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.normno.rutubedownloader"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "ru.normno.rutubedownloader.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "RuTube Downloader"
            packageVersion = "1.0.0"
            includeAllModules = false
            vendor = "normno.ru"
            modules(
                "java.base",
                "java.desktop"
            )
            windows{
                iconFile.set(project.file("src/commonMain/composeResources/drawable/rutubeDownloader_icon.ico"))
                menuGroup = "Video Tools"
                perUserInstall = true
                upgradeUuid = UUID.randomUUID().toString()
                shortcut = true
            }
        }
    }
}
