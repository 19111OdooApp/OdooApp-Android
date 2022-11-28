package odoo.miem.android.core.utils.network

import java.util.regex.Pattern

/**
 * For extracting database name
 *
 * Example:
 * https://cool.odoo.ru -> cool
 */
private val pattern by lazy { Pattern.compile(".+://(\\w+)\\..+") }

internal fun String.getDatabaseFromUrl(): String {
    return pattern.matcher(this).takeIf { it.find() }?.group(1) ?: ""
}