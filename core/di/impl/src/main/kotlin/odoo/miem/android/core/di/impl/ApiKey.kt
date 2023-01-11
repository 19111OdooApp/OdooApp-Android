package odoo.miem.android.core.di.impl

import dagger.MapKey
import odoo.miem.android.core.di.api.Api
import kotlin.reflect.KClass

/**
 * [ApiKey] - annotation, which is needed for convenient [Api] substitution
 * as key to shared **Map** after *@Provides* methods
 *
 * Use like this:
 * *@ApiKey(SomeStarFeatureApi::class)*
 *
 * @param value - parameter, which provides implementation by [Api] key
 *
 * @author Vorozhtsov Mikhail
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ApiKey(val value: KClass<out Api>)
