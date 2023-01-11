package odoo.miem.android.core.di.impl

import odoo.miem.android.core.di.api.Api

/**
 * Delegate for lazy injection of [Api] properties to class properties
 *
 * Use like this:
 * val logger by api(OdooApi::logger)
 */
inline fun <T, reified A : Api> api(crossinline getter: (A) -> T): Lazy<T> = lazy { getter(getApi()) }

/**
 * Delegate for lazy injection of [Api] properties to class properties
 *
 * Use like this:
 * val logger = apiBlocking(OdooApi::logger)
 */
inline fun <T, reified A : Api> apiBlocking(crossinline getter: (A) -> T): T = getter(getApi())

/**
 * Method for getting Api (kotlin)
 *
 * Use like this:
 * val someOdooFeatureApi: SomeOdooFeatureApi = getApi()
 */
inline fun <reified T : Api> getApi(): T = getApi(T::class.java)

/**
 * Method fo getting Api (java)
 *
 * Use like this:
 * SomeOdooFeatureApi someOdooFeatureApi = ApiHelpers.getApi(SomeOdooFeatureApi.class)
 */
fun <T : Api> getApi(cls: Class<T>): T = ApiRegistry.getApi(cls)
