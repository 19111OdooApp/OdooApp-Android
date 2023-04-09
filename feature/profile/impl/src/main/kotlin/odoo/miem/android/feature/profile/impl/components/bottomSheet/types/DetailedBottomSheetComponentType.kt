package odoo.miem.android.feature.profile.impl.components.bottomSheet.types

sealed interface DetailedBottomSheetComponentType {

    var result: String?

    data class BigTextComponentType(
        val placeholderText: String,
        override var result: String? = null
    ) : DetailedBottomSheetComponentType

    data class SmallTextComponentType(
        val placeholderText: String,
        override var result: String? = null
    ) : DetailedBottomSheetComponentType

    data class DatePickerComponentType(
        val placeholderText: String,
        override var result: String? = null
    ) : DetailedBottomSheetComponentType

    data class ListComponentType(
        val placeholderText: String,
        val values: List<String>,
        override var result: String? = null
    ) : DetailedBottomSheetComponentType
}