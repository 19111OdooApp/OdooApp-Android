package odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.searchLike.SearchLikeScreen
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.stateholder.error.ErrorScreen
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.feature.employees.api.IEmployeesScreen
import odoo.miem.android.feature.employees.impl.EmployeesViewModel
import odoo.miem.android.feature.employees.impl.R
import odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees.components.EmployeesList
import odoo.miem.android.feature.navigation.api.data.Routes
import javax.inject.Inject

/**
 * [EmployeesScreen] implementation of [IEmployeesScreen]
 *
 * Methods by purpose:
 * - [EmployeesScreen] - the input point to this screen is needed for initial initialization.
 * For example, ViewModel initialization
 * - [EmployeesScreenContent] - directly layout of this screen
 * - [EmployeesScreenPreview] - preview of the layout that turned out in [EmployeesScreenContent]
 *
 * @author Egor Danilov
 */
class EmployeesScreen @Inject constructor() : IEmployeesScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun EmployeesScreen(
        viewModelStoreOwner: ViewModelStoreOwner,
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: EmployeesViewModel = viewModel(viewModelStoreOwner)

        val userInfoState by viewModel.userInfoState.collectAsState()
        val allEmployeesState by viewModel.allEmployeesState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onEmployeesOpen()
        }

        StateHolder(
            state = allEmployeesState,
            loadingContent = { LoadingScreen() },
            errorContent = {
                ErrorScreen(
                    isSessionExpired = it.isSessionExpired,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.onEmployeesOpen() }
                )
            },
            successContent = {
                EmployeesScreenContent(
                    userName = userInfoState.data?.name ?: "Cool user",
                    allEmployees = viewModel.allEmployeesList,
                    onEmployeeClick = {
                        navController.navigate("${Routes.employeeDetails}/${it.id}")
                    },
                    onNavigateToModulesPressed = {
                        navController.navigate(Routes.selectingModules)
                    },
                    onUserIconClick = {
                        navController.navigate(Routes.userProfile)
                    },
                    onBackPressed = navController::popBackStack
                )
            }
        )
    }

    @Composable
    private fun EmployeesScreenContent(
        userName: String,
        allEmployees: List<EmployeeBasicInfo> = emptyList(),
        onEmployeeClick: (EmployeeBasicInfo) -> Unit = {},
        onNavigateToModulesPressed: () -> Unit = {},
        onUserIconClick: () -> Unit = {},
        onBackPressed: () -> Unit = {}
    ) {
        val content: @Composable (ColumnScope.(items: List<EmployeeBasicInfo>) -> Unit) = {
            EmployeesList(
                employees = allEmployees,
                onClick = onEmployeeClick
            )
        }

        BackHandler(enabled = true) {
            onBackPressed()
        }

        SearchLikeScreen(
            items = allEmployees,
            userName = userName,
            searchBarPlaceholder = R.string.employees_search_bar_placeholder,
            mainTitle = stringResource(R.string.employees_main_title),
            mainListContent = content,
            searchResultListContent = content,
            onNavigateToModulesPressed = onNavigateToModulesPressed,
            onUserIconClick = onUserIconClick
        )
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun EmployeesScreenPreview() = OdooMiemAndroidTheme {
        EmployeesScreenContent(
            "Cool user",
            allEmployees = listOf(
                EmployeeBasicInfo(
                    id = 0L,
                    name = "Arina Shoshina",
                    job = "Consultant",
                    email = "abigail.peterson39@example.com",
                    phone = "(482)-233-3393",
                    avatar = "iVBORw0KGgoAAAANSUhEUgAAALQAAAC0CAYAAAA9zQYyAAAABmJLR0QAAAAAAAD5Q7t/A" + // sorry...
                        "AAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH5AwPCRwxZOpoiwAACUJJREFUeNrtnWt" +
                        "3mkoUhl8IdxAh5Pb//1trolEDqOAgw/lwlp6mJ01iahRm3metrvZbZeZxs/eei4aUsgMhi" +
                        "mByCAiFJoRCE0KhCaHQhEITQqEJodCEUGhCKDSh0IRQaEIoNCEUmhAKTSg0IRSaEApNCIU" +
                        "mhEITCk0IhSaEQhNCoQmh0IRCE0KhCaHQhFBoQig0odCEUGhCKDQhFJoQABaH4Hi6rkPTN" +
                        "KjrGlVVoa5rCCEgpUTXdYc/b2EYBgzDOPzbNE0YhgHbtmFZFmzbRhAE8H0fV1dXHOwjMfg" +
                        "bK8dRVRXKskRVVWiaBrvdDlLKk/4ftm3D8zx4nocoiuC6LkyTL1MKfSKklCjLEvP5HHVdn" +
                        "/3/9zwPNzc3iKKIYlPoryOEQFmWKIoCVVVd/PP4vo/RaITxeAzbtjlBFPq4iDybzSCE6N3" +
                        "ncxwHd3d3iOOYk0WhP47K8/kcy+Wy9581TVNkWQbHcThxFPr/nYv1eo3Hx8deRuX30pCHh" +
                        "wf4vs9JpNCvZZ5MJmiaZnCf37ZtPDw8IAxD7YtG7YWWUiLPc0ynU7RtO9jnsG0b9/f32uf" +
                        "V2veAqqoavMwA0DQNJpNJL7oxFPpC1HWNp6enwcu8p21b/PjxQ2uptRVaCIHJZHKRhZLvf" +
                        "q7ZbHby1UsK3fMiMM9zZSPZarXCcrnUUmothd5ut3h5eVH6GWezGdbrNYVWHSnlYNtzxz7" +
                        "nYrFQ/jm1Flr1VON3qqrSrkDUSujdboeiKLR6G+mWS2sl9Ha7Va6r8RGbzQZlWVJoVdMNV" +
                        "XrOxzx3URTaPLc2Qm+3W6xWK+jI/ogYhVYoSpVlqV103tM0jTYtPC2E3u122kbnPev1+o8" +
                        "Hdyn0wBBCaPPKfS/t0KEnrY3Quu5t2NO2LTabDYVWRWgdXrefidKqj4MWQuu2/PveOKj+p" +
                        "lJeaCmltt0NHb/YWkRoCv3fODBCcxKVgjk0J1Cp9IspB6VWahyYcjAyKcP+Gl8KTQiFJoR" +
                        "CE0KhCdFKaN56T6FZ3RMK3VfYh9ZnHPgu1oj9z8hR6IFPIlMOphyExTGFJoRCnyEqsW33O" +
                        "gWj0AOHm5MYoVnZcywodB/puo5dDkZo5oyq1hMUmkJzLCg0I1Nfx4FdDqJcTUGhBz6BjNC" +
                        "M0ErljcyjmUNzIjkOFJoRmikHhWZkOus4sChUJDIRRmgKrRhXV1cUmpFJnXSDS98KRSbd6" +
                        "boOlmVRaBWwLEv7CG2aJmzbptDMHdUZA6YcCk2mDtHpo7cUUw4KrQyO4zBCq5Q/uq6rrcy" +
                        "GYcDzPD1qBV0mNAxDbfvRFFpBfN/XNko7jgPHcSi0amnHaDTSstsRRZEWBaFWQgNAGIbaT" +
                        "OyvBXEcx9p8kbUS2nEcbXLJX7/EOqVaWgltmiaiKNLqeXVLs7QSet/t0GVvh+M48H1fqze" +
                        "Sdn0sx3GQJAlrBgqtDmmaahGl4zjWrveupdCWZSmfS0dRpGXfXUuh98WhqsWSaZrIskzLl" +
                        "VFtzyYFQaDshiXf97UrBrUX2rIspGmq3HMZhqH024dCvzPxSZIot8dB5yV+rYUG1FwWTpJ" +
                        "E673f2p/vj+NYmV6tbdtI01Tr42baC+04DsIwVObLqcs2UQr9Ts6pwkKL4ziI4xjazycIX" +
                        "NfFaDQa9DMEQaD1MTMK/UaUHmoxdXV1hfF4zCvPKPTrKD3U5fAkSZSpAyj0CaN0kiSDy6U" +
                        "dx8F4POYEUuj/43ne4LaWjsdj7TsbFPoPGIaBm5ubwRzT2kdn5s4U+t0CK03T3ktimibu7" +
                        "u4YnSn0x8Rx3PvdanEcs+9MoT8fpfsc/RzHUXKnIIX+RnzfR5ZlvdsXYZombm9vtbuOgUK" +
                        "fgCiKevVaNwwDaZpqvT2UQv8Ftm0jy7LeRMMgCAZRsFLoniKlPGzJ7INEQRDAMAxIKTk5f" +
                        "3qLSSk7DsN/AnddByEEqqrCdruFEAJN00AI0Yti1bZtuK57uNbMdV3Yts0UhEL/S9d1kFK" +
                        "iqirkeY7VaoW2bYczgYaBIAgwGo0QRZH2cmspdNu2hyhcVRU2mw2aphl+/mia8H0fnucd/" +
                        "rYsS6ucWyuhm6bBarVCURSo63pQkfgrcluWhTAMkSQJXNfVQmylhe66DrvdDkIIFEWBoii" +
                        "Ulvg9wjA8rICq/ANCygothEBZliiKAkIIbUX+Pd+2bfuV3KqJrZTQUkrsdjvkeY7FYkGJP" +
                        "yCOY6RpqpTYSggtpcRms0FZlliv171osQ0p1/69S0KhL5gj13WN+XyOsizRdWyp/w2O4+D" +
                        "m5mbQ1/AOUui2bVFV1SFHZmpx2jw7CAKMx+PDhelD6msPSmgpJbbbLebzOVarFZeAvxnf9" +
                        "5EkyaBOxQxCaCklhBDI8xzL5ZIinzlih2GINE0RBEHvDxH3XmghBJbLJcqyZLF34eIxiqL" +
                        "ed0V6K3TbtsjzHLPZjDlyz0jTFFmW9XLfSO+Ebtv2sKq3Xq9pT487IuPxuHfXKPRK6KqqM" +
                        "J1OKfLAxM6yrDeF48WF3hd8y+USeZ6z4Bsoo9EI19fX8DzvooXjRYVmwade4TgajZBlGVz" +
                        "XvUh+fXah9xvqy7LE8/MzRVZU7DRNDze6nlPsswrdti3KskSe59hsNlyq1iC/TpLkrL8sc" +
                        "Dah1+s1ptMpqqriTGso9rn2iHyr0Ps9F3meoyxLFnyas9+HHYbht6Ui3yL0r7vguOeCvBL" +
                        "OMOC6LrIsw2g0OnnEPqnQ3HNBjo3YaZoiDMOTtfpOJnTTNFgsFiiKQokT1OQ8mKaJMAxxf" +
                        "X19uEjnokJLKbFarTCdTtmCI19mfy93mqZ/tQf7y0J3XYftdnuIykwvyCnY3/r61Qspvyx" +
                        "0URSMyuTb0pAkSXB7e3t0bn200E3T4OXlBfP5nFGZfCtRFOH29vaoX1M4SmghBKbTKQ+kk" +
                        "rPhOA7u7u4QRdGnWnyfEnqfL08mE670kYsUjPf3959aafxUV7uua8pMLkbbtnh6esLLy8u" +
                        "Hae6HQjdNg8fHR8pMLi71Pt39stBCCEZm0huklJjNZu+eaDLfy5uXyyVWqxVHkvQGIQR+/" +
                        "vz5x3bxH4XebDZYLpccQdI7mqbB8/Pzm7cBmO+lGuwzk75SFMWbqbD5VvK9v1OZkL7n0x8" +
                        "Kvf/JBkL6zqci9GKxQF3XHC0ySMzPWE/IYIUmhEITQqEJodCEUGhCoQmh0IRQaEJOyD/96" +
                        "qPJT5V/3AAAAABJRU5ErkJggg=="
                )
            )
        )
    }
}
