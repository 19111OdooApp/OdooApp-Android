package odoo.miem.android.core.networkApi.selectingModules.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooGroup

/**
 * [IGetModules] - interface for making Retrofit instance of getting modules
 *
 * @author Egor Danilov
 */
interface IGetModules: JsonRpcApi {

    @JsonRpc("call")
    fun getUserGroups(
        @JsonArgument("service") service: String,
        @JsonArgument("method") method: String = "login",
        @JsonArgument("args") args: List<Any> = emptyList()
    ): List<OdooGroup>
}