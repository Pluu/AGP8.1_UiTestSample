import com.android.build.api.variant.ResValue

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.pluu.agp80_uitestsample"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.pluu.agp80_uitestsample"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

// UITest Checker
androidComponents {
    onVariants(selector().withBuildType("debug")) { variant ->
        println("onVariants ==> " + variant.name)
        if (isUiTestStartGradle()) {
            variant.applicationId.set("pluu.pluu")
            variant.resValues.put(
                variant.makeResValueKey("string", "app_name"),
                ResValue("AGP_SAMPLE")
            )
            println("onVariants - Run UiTest")
        } else {
            println("onVariants - Run Dev")
        }
    }
}

fun isUiTestStartGradle(): Boolean {
    val regex = "connected.*AndroidTest\$".toRegex()
    return gradle.startParameter.taskNames.any {
        regex.containsMatchIn(it)
    }
}