package odoo.miem.android.core.utils.di

import dagger.Component

@Component(
    modules = [
        RxModule::class
    ]
)
interface RxComponent : RxApi {
    companion object {
        fun create(): RxApi = DaggerRxComponent.builder()
            .build()
    }
}
