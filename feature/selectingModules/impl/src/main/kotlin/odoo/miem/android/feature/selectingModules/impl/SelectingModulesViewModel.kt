package odoo.miem.android.feature.selectingModules.impl

import odoo.miem.android.core.di.impl.apiBlocking
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.di.RxApi
import odoo.miem.android.core.utils.rx.PresentationSchedulers

/**
 * [SelectingModulesViewModel] handle major logic for [SelectingModulesScreen]
 *
 * @author Vorozhtsov Mikhail
 */
class SelectingModulesViewModel(
    schedulers: PresentationSchedulers = apiBlocking(RxApi::presentationSchedulers)
) : BaseViewModel(schedulers) {

}
