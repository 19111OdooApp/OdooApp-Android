package odoo.miem.android.feature.profile.impl.components

import odoo.miem.android.feature.profile.impl.components.bottomSheet.types.DetailedBottomSheetComponentType
import odoo.miem.android.feature.profile.impl.data.DividedListItem
import java.util.*

sealed interface PagesType

interface TopicableType : PagesType {
    val topic: String
}

data class DetailedInfoType(
    val blocks: Map<String, List<DetailedInfoFieldType>>
) : PagesType {

    interface DetailedInfoFieldType {
        val key: String
    }

    class TextType(
        override val key: String,
        val text: String
    ) : DetailedInfoFieldType

    class NumberType(
        override val key: String,
        val number: Float
    ) : DetailedInfoFieldType

    class RatingType(
        override val key: String,
        val rating: Double,
        val starsCount: Int = 3
    ) : DetailedInfoFieldType

    class DateType(
        override val key: String,
        val date: Date
    ) : DetailedInfoFieldType
}

data class TextType(
    override val topic: String,
    val text: String
) : TopicableType

interface DividedListType : TopicableType {

    val items: List<DividedListItem>

    val sheetElements: List<DetailedBottomSheetComponentType>

    val bottomSheetButtonText: String
}
