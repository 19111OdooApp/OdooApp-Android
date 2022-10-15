package odoo.miem.android.di

/**
 * [ApiProvider] - SAM интерфейс для обертки реализации [Api] и последующего добавления
 * в общий **Map** у [ApiResolver]
 *
 * Используется так:
 * ApiProvider { SomeStarFeatureApiProvider.create() }
 *
 * @see ApiResolver
 *
 * @author Ворожцов Михаил
 */
fun interface ApiProvider {
    fun get(): Api
}