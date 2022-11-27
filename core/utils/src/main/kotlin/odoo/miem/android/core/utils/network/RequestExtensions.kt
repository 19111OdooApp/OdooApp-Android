package odoo.miem.android.core.utils.network

/**
 * For extracting database name
 *
 * Example:
 * https://cool.odoo.ru -> cool
 */
internal fun String.getDatabaseFromUrl() = this.substring(
    startIndex = this.lastIndexOf("://") + 3, // Yep, +3 because of ://
    endIndex = this.indexOf(".")
)