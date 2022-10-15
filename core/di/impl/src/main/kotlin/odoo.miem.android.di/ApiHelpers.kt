package odoo.miem.android.di

/**
 * Делегат для lazy инжектирования свойств [Api] в properties классов.
 *
 * Используется так:
 * val logger by api(OdooApi::logger)
 */
inline fun <T, reified A : Api> api(crossinline getter: (A) -> T): Lazy<T> = lazy { getter(getApi()) }

/**
 * Метод получения Api (kotlin)
 *
 * Используется так:
 * val someOdooFeatureApi: SomeOdooFeatureApi = getApi()
 */
inline fun <reified T : Api> getApi(): T = getApi(T::class.java)

/**
 * Метод получения Api (java)
 *
 * Используется так:
 * SomeOdooFeatureApi someOdooFeatureApi = ApiHelpers.getApi(SomeOdooFeatureApi.class)
 */
fun <T : Api> getApi(cls: Class<T>): T = ApiRegistry.getApi(cls)