package odoo.miem.android.core.retrofitApiFabric

import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi

/**
 * [RetrofitApiProvider] - SAM interface for wrapping instance of [RetrofitApi] and providing in
 * general **Map** of [RetrofitApiResolver]
 *
 * Usage:
 * RetrofitApiResolver { SomeRetrofitApiProvider.create() }
 *
 * @see RetrofitApiResolver
 *
 * @author Vorozhtsov Mikhail
 */
fun interface RetrofitApiProvider {
    fun get(): RetrofitApi
}
