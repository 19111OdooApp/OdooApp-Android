package odoo.miem.android.common.uiKitComponents.screen.base.searchLike.model

data class ScreenPage<T>(
    val maxSize: Int? = null,
    val currentPage: Int? = null,
    val pageSize: Int? = null,
    val fromIndex: Int? = null,
    val toIndex: Int? = null,
    val items: List<T>
)