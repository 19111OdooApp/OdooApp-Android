package odoo.miem.android.core.di.impl

import dagger.MapKey
import odoo.miem.android.core.di.api.Api
import kotlin.reflect.KClass

/**
 * [ApiKey] - аннотация, которая нужна для удобной подстановки [Api]
 * как ключа в общую **Map** после *@Provides* методов
 *
 * Используется так:
 * *@ApiKey(SomeStarFeatureApi::class)*
 *
 * @param value параметр, который предоставляет реализацию под ключом [Api]
 *
 * @author Ворожцов Михаил
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ApiKey(val value: KClass<out Api>)
