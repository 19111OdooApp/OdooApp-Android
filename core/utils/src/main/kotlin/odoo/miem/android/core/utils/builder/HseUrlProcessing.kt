package odoo.miem.android.core.utils.builder

import odoo.miem.android.core.utils.regex.getDomainFromUrl
import timber.log.Timber

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
