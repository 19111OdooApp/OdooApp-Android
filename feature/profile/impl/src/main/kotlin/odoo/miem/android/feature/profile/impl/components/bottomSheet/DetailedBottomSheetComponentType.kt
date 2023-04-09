package odoo.miem.android.feature.profile.impl.components.bottomSheet

import java.util.*

sealed interface DetailedBottomSheetComponentType {

    data class BigTextComponentType(
        val placeholderText: String,
        val onDone: () -> String
    ) : DetailedBottomSheetComponentType

    data class SmallTextComponentType(
        val placeholderText: String,
        val onDone: () -> String
    ) : DetailedBottomSheetComponentType

    data class DatePickerComponentType(
        val placeholderText: String,
        val onDone: () -> Date
    ) : DetailedBottomSheetComponentType

    data class ListComponentType(
        val placeholderText: String,
        val values: List<String>,
        val onDone: () -> String
    ) : DetailedBottomSheetComponentType
}