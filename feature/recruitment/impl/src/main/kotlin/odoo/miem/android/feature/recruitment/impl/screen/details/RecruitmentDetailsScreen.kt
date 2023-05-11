package odoo.miem.android.feature.recruitment.impl.screen.details

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.DetailsLikeScreen
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DividedListType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.bottomSheet.types.DetailedBottomSheetComponentType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeDividedListItem
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeHeader
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DividedListItemAction
import odoo.miem.android.core.uiKitTheme.odooGray
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.feature.recruitment.api.IRecruitmentDetailsScreen
import odoo.miem.android.feature.recruitment.impl.R
import odoo.miem.android.feature.recruitment.impl.RecruitmentViewModel
import java.util.Date
import javax.inject.Inject

/**
 * [RecruitmentDetailsScreen] implementation of [IRecruitmentDetailsScreen]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentDetailsScreen @Inject constructor() : IRecruitmentDetailsScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun RecruitmentDetailsScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: RecruitmentViewModel = viewModel()

        RecruitmentDetailsScreenContent(
            navigateBack = navController::popBackStack
        )
    }

    // TODO Delete mock
    @Composable
    fun RecruitmentDetailsScreenContent(
        navigateBack: () -> Unit = {},
    ) = DetailsLikeScreen(
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
                            number = 0F
                        ),
                        DetailedInfoType.NumberType(
                            key = "Proposed Salary",
                            number = 0F
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
                                    iconRes = R.drawable.ic_cancel,
                                    background = odooGray,
                                    onSwipe = { }
                                ),
                                DividedListItemAction(
                                    iconRes = R.drawable.ic_cancel,
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

                override val bottomSheetButtonText: String = "Add new log note"
            }
        ),
        navigateBack = navigateBack
    )

    @Preview(showBackground = true)
    @Composable
    fun RecruitmentDetailsScreenPreview() {
        RecruitmentDetailsScreenContent()
    }
}
