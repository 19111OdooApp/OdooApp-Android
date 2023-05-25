package odoo.miem.android.feature.crm.impl.screen.details

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.DetailsLikeScreen
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DividedListType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.PagesType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.bottomSheet.types.DetailedBottomSheetComponentType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeDividedListItem
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeHeader
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DividedListItemAction
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.stateholder.error.ErrorScreen
import odoo.miem.android.core.uiKitTheme.odooGray
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.crm.api.ICrmDetailsScreen
import odoo.miem.android.feature.crm.impl.CrmViewModel
import odoo.miem.android.feature.crm.impl.R
import odoo.miem.android.feature.crm.impl.screen.details.helpers.getDetailsHeader
import odoo.miem.android.feature.crm.impl.screen.details.helpers.getDetailsPages
import odoo.miem.android.feature.navigation.api.data.Routes
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

/**
 * [CrmDetailsScreen] implementation of [ICrmDetailsScreen]
 *
 * @author Vorozhtsov Mikhail
 */
class CrmDetailsScreen @Inject constructor() : ICrmDetailsScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun CrmDetailsScreen(
        opportunityId: Long,
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: CrmViewModel = viewModel()
        val context = LocalContext.current

        Timber.d("CrmDetailsScreen: opportunityId - $opportunityId")

        val opportunityInfoState by viewModel.opportunityInfoState.collectAsState()
        opportunityInfoState.subscribeOnError(showMessage)

        LaunchedEffect(Unit) {
            viewModel.onOpenDetails(opportunityId)
        }

        StateHolder(
            state = opportunityInfoState,
            loadingContent = { LoadingScreen() },
            errorContent = {
                ErrorScreen(
                    isSessionExpired = it.isSessionExpired,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.onOpenDetails(opportunityId) }
                )
            },
            successContent = { applicationInfo ->
                applicationInfo.data?.let {
                    CrmDetailsScreenContent(
                        header = getDetailsHeader(
                            opportunityInfo = it,
                            majorTitle = context.resources.getString(R.string.crm_details_title_header_major)
                                .format(
                                    it.expectedRevenue,
                                    it.companyCurrencySymbol,
                                    it.probability
                                )
                        ),
                        pages = getDetailsPages(
                            stringResolver = { res -> context.resources.getString(res) },
                            opportunityInfo = it,
                            onCreateLogNote = { viewModel.createLogNote(opportunityId, it) },
                            context = LocalContext.current
                        ),
                        navigateBack = navController::popBackStack
                    )
                } ?: ErrorScreen(
                    isSessionExpired = false,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.onOpenDetails(opportunityId) }
                )
            }
        )
    }

    @Composable
    fun CrmDetailsScreenContent(
        header: DetailsLikeHeader,
        pages: List<PagesType> = emptyList(),
        navigateBack: () -> Unit = {},
    ) = DetailsLikeScreen(
        header = header,
        pages = pages,
        navigateBack = navigateBack
    )

    @Preview(showBackground = true)
    @Composable
    fun RecruitmentDetailsScreenPreview() {
        CrmDetailsScreenContent(
            header = DetailsLikeHeader(
                title = "Arina Shoshina",
                majorSubtitle = "aashoshina@miem.hse.ru",
                minorSubtitle = "+79013686745"
            ),
            pages = listOf(
                DetailedInfoType(
                    blocks = mapOf(
                        "Job" to listOf(
                            DetailedInfoType.TextType(
                                key = "Applied Job",
                                text = "УЛ СВТ"
                            ),
                            DetailedInfoType.TextType(
                                key = "Department",
                                text = ""
                            ),
                            DetailedInfoType.TextType(
                                key = "Recruter’s project",
                                text = ""
                            ),
                            DetailedInfoType.TextType(
                                key = "Person’s group",
                                text = ""
                            ),
                            DetailedInfoType.TextType(
                                key = "Tags",
                                text = ""
                            ),
                            DetailedInfoType.TextType(
                                key = "Recruiter",
                                text = "Королев Денис Александрович"
                            ),
                            DetailedInfoType.DateType(
                                key = "Hire Date",
                                date = Date()
                            ),
                            DetailedInfoType.RatingType(
                                key = "Appreciation",
                                rating = 2.0
                            ),
                            DetailedInfoType.TextType(
                                key = "Source",
                                text = ""
                            ),
                            DetailedInfoType.TextType(
                                key = "Test task",
                                text = ""
                            )
                        ),

                        "Contract" to listOf(
                            DetailedInfoType.NumberType(
                                key = "Expected Salary",
                                number = 0.0
                            ),
                            DetailedInfoType.NumberType(
                                key = "Proposed Salary",
                                number = 0.0
                            )
                        )
                    )
                ),
                TextType(
                    topic = "Application Summary",
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Mauris luctus consequat est. " +
                        "Praesent viverra nisl felis, eget pharetra mauris bibendum ac." +
                        " Sed justo orci, blandit vehicula vestibulum quis, interdum in eros." +
                        " Sed a eros luctus risus pharetra consequat. Vivamus mollis a lectus quis elementum." +
                        " Integer vel nibh at nulla faucibus consequat. Morbi placerat tortor ut orci mattis," +
                        " ut dapibus risus porta. Pellentesque habitant morbi tristique senectus et netus" +
                        " et malesuada fames ac turpis egestas. Cras luctus tempus est ut malesuada."
                ),
                // Log note
                object : DividedListType {
                    override val topic: String = "Log note"

                    override val items: List<DetailsLikeDividedListItem> = listOf(
                        object : DetailsLikeDividedListItem {
                            override val topic: String
                                get() = "Ворожцов Михаил"
                            override val description: String
                                get() = "Some cool descri[tion"
                            override val avatarUrl: String?
                                get() = null
                            override val userName: String
                                get() = "Ворожцов Михаил"
                            override val date: String
                                get() = "12-12-2000"
                            override val actions: List<DividedListItemAction>
                                get() = listOf(
                                    DividedListItemAction(
                                        iconRes = R.drawable.ic_info,
                                        background = odooGray,
                                        onSwipe = { }
                                    ),
                                    DividedListItemAction(
                                        iconRes = R.drawable.ic_info,
                                        background = odooPrimary,
                                        onSwipe = { }
                                    ),
                                )
                        },
                    )
                    override val sheetElements: List<DetailedBottomSheetComponentType> = listOf(
                        DetailedBottomSheetComponentType.BigTextComponentType(
                            placeholderText = "Enter log note..."
                        ),
                        DetailedBottomSheetComponentType.SmallTextComponentType(
                            placeholderText = "Summary"
                        ),
                        DetailedBottomSheetComponentType.ListComponentType(
                            placeholderText = "Assigned to",
                            values = listOf(
                                "Arina Shoshina1",
                                "Arina Shoshina2",
                                "Arina Shoshina3",
                                "Arina Shoshina4",
                            )
                        ),
                        DetailedBottomSheetComponentType.DatePickerComponentType(
                            placeholderText = "Due Date"
                        ),
                    )
                    override val onDone: (results: List<DetailedBottomSheetComponentType>) -> Unit
                        get() = {}

                    override val bottomSheetButtonText: String = "Add new log note"
                }
            )
        )
    }
}
