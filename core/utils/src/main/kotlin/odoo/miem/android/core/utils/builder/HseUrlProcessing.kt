package odoo.miem.android.core.utils.builder

import timber.log.Timber
import java.util.regex.Pattern

private val domainPattern by lazy { Pattern.compile(".+://(.*)/") }

internal fun String.getDomainFromUrl(): String {
    return domainPattern.matcher(this).takeIf { it.find() }?.group(1) ?: ""
}

fun urlProcessing(rawUrl: String): String {
    var proceededUrl = if (!rawUrl.run { startsWith("https://") || startsWith("http://") }) {
        "https://"
    } else {
        ""
    } + rawUrl.trim()

    if (!rawUrl.endsWith("/")) {
        proceededUrl += "/"
    }

    Timber.d("domainUrlProcessing(): proceededUrl - $proceededUrl")

    return proceededUrl
}

fun domainProcessing(rawUrl: String) = urlProcessing(rawUrl).getDomainFromUrl()