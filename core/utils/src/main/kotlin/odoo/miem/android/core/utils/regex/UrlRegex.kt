package odoo.miem.android.core.utils.regex

import java.util.regex.Pattern

// TODO Description
private val domainPattern by lazy { Pattern.compile(".+://(.*)/") }

internal fun String.getDomainFromUrl(): String {
    return domainPattern.matcher(this).takeIf { it.find() }?.group(1) ?: ""
}