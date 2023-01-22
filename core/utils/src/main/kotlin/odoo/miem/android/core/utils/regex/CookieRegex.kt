package odoo.miem.android.core.utils.regex

import java.util.regex.Pattern

/**
 * For extracting session id from cookie
 *
 * Example:
 * session_id=superC00lsess1on1d -> superC00lsess1on1d
 */
private val sessionIdPattern by lazy { Pattern.compile("session_id=(\\w+)") }

fun String.getSessionIdFromCookie(): String {
    return sessionIdPattern.matcher(this).takeIf { it.find() }?.group(1) ?: ""
}
