package odoo.miem.android.core.retrofitApiFabric

import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi

// TODO Description
fun interface RetrofitApiProvider {
    fun get() : RetrofitApi
}