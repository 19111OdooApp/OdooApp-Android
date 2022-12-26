package odoo.miem.android.feature.selectingModules.impl

import android.database.Observable
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ResultSubject
import odoo.miem.android.feature.selectingModules.impl.data.OdooModule
import java.util.Locale

/**
 * [SelectingModulesViewModel] handle major logic for [SelectingModulesScreen]
 *
 * @author Vorozhtsov Mikhail
 */
class SelectingModulesViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val allModulesState: ResultSubject<List<OdooModule>> by lazyEmptyResultPublishSubject()
    val searchState: ResultSubject<List<OdooModule>> by lazyEmptyResultPublishSubject()
//
//    fun getAllModules() {
//        allModulesState.onLoadingState()
//        val observable = Observable<List<OdooModule>>()
//    }

    fun performSearch(condition: String) {

    }

    // TODO DELETE TEST DATA
    private val modules = listOf(
        OdooModule(
            name = "CRM",
            numberOfNotifications = 1
        ),
        OdooModule(
            name = "Recruitment",
            numberOfNotifications = 5,
            isLiked = true
        ),
        OdooModule(
            name = "Pricing",
            numberOfNotifications = 123
        ),
    )

    private fun String.matchesCondition(condition: String) =
        lowercase(Locale.getDefault()).matches(Regex(".*$condition.*"))
}
