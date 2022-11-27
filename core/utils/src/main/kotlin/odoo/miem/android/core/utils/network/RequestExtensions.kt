package odoo.miem.android.core.utils.network

internal fun String.getDatabaseFromUrl() = this.substring(
    startIndex = this.lastIndexOf("://") + 3, // Yep, +3 because of ://
    endIndex = this.indexOf(".")
)