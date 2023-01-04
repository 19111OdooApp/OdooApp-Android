package odoo.miem.android.core.utils.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.utils.rx.PresentationSchedulers

interface RxApi : Api {
    val presentationSchedulers: PresentationSchedulers
}
