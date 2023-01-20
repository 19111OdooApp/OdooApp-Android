package odoo.miem.android.core.utils.regex

import java.util.regex.Pattern

// TODO Description
private val sessionIdPattern by lazy { Pattern.compile("session_id=(\\w+)") }

fun String.getSessionIdFromCookie(): String {
    return sessionIdPattern.matcher(this).takeIf { it.find() }?.group(1) ?: ""
}