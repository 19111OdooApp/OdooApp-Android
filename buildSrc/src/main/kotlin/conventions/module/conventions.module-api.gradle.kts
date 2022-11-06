import gradle.kotlin.dsl.accessors._b8e9b34270198d14a4ec0d8967890a50.api
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

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