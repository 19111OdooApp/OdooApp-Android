/**
 * [Dependencies] предназначен для хранения всех зависимостей, которые нужны
 * в данном проекте
 *
 *
 * @author Ворожцов Михаил
 * @since 09.10.2022
 */
object Dependencies {

    object AndroidCore {
        private const val APPCOMPAT_VERSION = "1.5.0"
        private const val CORE_VERSION = "1.8.0"

        const val APPCOMPAT = "androidx.appcompat:appcompat:$APPCOMPAT_VERSION"
        const val CORE = "androidx.core:core-ktx:$CORE_VERSION"

        val ALL_DEPS = listOf(APPCOMPAT, CORE)
    }

    object Coroutines {
        private const val VERSION = "1.6.4"

        const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION"
    }

    object Dagger {
        private const val VERSION = "2.44"

        const val ANDROID = "com.google.dagger:dagger-android:$VERSION"
        const val COMPILER = "com.google.dagger:dagger-compiler:$VERSION"
        const val PROCESSOR = "com.google.dagger:dagger-android-processor:$VERSION"

        val KAPT_DEPS = listOf(COMPILER, PROCESSOR)
    }

    object Logger {
        private const val TIMBER_VERSION = "5.0.1"

        const val TIMBER = "com.jakewharton.timber:timber:$TIMBER_VERSION"
    }

    object Retrofit {
        private const val VERSION = "2.9.0"

        const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:$VERSION"
        const val RETROFIT_DEPENDENCY = "com.squareup.retrofit2:retrofit:$VERSION"

        val ALL_DEPS = listOf(CONVERTER_GSON, RETROFIT_DEPENDENCY)
    }

    object Test {
        private const val JUNIT_VERSION = "1.1.3"
        private const val MOCKK_VERSION = "1.12.4"

        const val JUNIT = "androidx.test.ext:junit:$JUNIT_VERSION"
        const val TEST_JUNIT = "test-junit"
        const val MOCKK = "io.mockk:mockk:$MOCKK_VERSION"
    }

    object UI {
        private const val CONSTRAINT_LAYOUT_VERSION = "2.1.4"
        private const val MATERIAL_VERSION = "1.6.1"

        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"
        const val CORE = "com.google.android.material:material:$MATERIAL_VERSION"

        val ALL_DEPS = listOf(CONSTRAINT_LAYOUT, CORE)
    }

    object Plugins {
        const val DETEKT_VERSION = "1.22.0-RC1"

        const val DETEKT_FORMATTING = "io.gitlab.arturbosch.detekt:detekt-formatting:$DETEKT_VERSION"
    }
}
