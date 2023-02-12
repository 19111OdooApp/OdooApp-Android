package odoo.miem.android.core.utils.regex

import java.util.regex.Pattern

private val moduleIdPattern by lazy { Pattern.compile("\"id\":\\s?(\\d+),") }

fun String.getModulesIdFromFirebaseJson(): List<Int> {
    val matcher = moduleIdPattern.matcher(this)
    val modulesIds = mutableListOf<Int>()

    while (matcher.find()) {
        matcher.group(1)
            ?.let { modulesIds.add(it.toInt()) }
    }

    return modulesIds
}
