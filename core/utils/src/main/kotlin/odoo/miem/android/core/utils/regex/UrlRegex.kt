package odoo.miem.android.core.utils.regex

import java.util.regex.Pattern

/**
 * For extracting domain from url
 *
 * Example:
 * https://odoo.com/web/dataset/ -> odoo.com
 */
private val domainPattern by lazy { Pattern.compile(".+://(.*)/") }

internal fun String.getDomainFromUrl(): String {
    return domainPattern.matcher(this).takeIf { it.find() }?.group(1) ?: ""
}