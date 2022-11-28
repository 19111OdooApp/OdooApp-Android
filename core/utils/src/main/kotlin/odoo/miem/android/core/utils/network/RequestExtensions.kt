package odoo.miem.android.core.utils.network

/**
 * For extracting database name
 *
 * Example:
 * https://cool.odoo.ru -> cool
 */
internal fun String.getDatabaseFromUrl(): String {
    val pattern = ".+://(\\w+)\\..+".toRegex()
    return pattern.find(this)?.groupValues?.get(1) ?: ""
}