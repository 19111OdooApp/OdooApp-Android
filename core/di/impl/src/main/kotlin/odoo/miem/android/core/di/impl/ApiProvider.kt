package odoo.miem.android.core.di.impl

import odoo.miem.android.core.di.api.Api

/**
 * [ApiProvider] - SAM interface for wrapping implementation of [Api] and subsequent addition
 * to general **DI Map** of [ApiResolver]
 *
 * Use like this:
 * ApiProvider { SomeStarFeatureApiProvider.create() }
 *
 * @see ApiResolver
 *
 * @author Vorozhtsov Mikhail
 */
fun interface ApiProvider {
    fun get(): Api
}
