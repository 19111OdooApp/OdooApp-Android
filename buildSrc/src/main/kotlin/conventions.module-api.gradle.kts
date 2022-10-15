import org.gradle.kotlin.dsl.dependencies

/**
 * Конвеция для api модулей, которая содержит [conventions.base-api] и
 * добавляет необходимые зависимости
 *
 * @author Ворожцов Михаил
 * @since 09.10.2022
 */
plugins {
    id("conventions.base-api")
}

dependencies {

    // DI
    api(project(":core:di:api"))
}