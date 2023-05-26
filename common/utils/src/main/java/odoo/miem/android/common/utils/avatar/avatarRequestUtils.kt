package odoo.miem.android.common.utils.avatar

fun getAvatarLink(userId: Long, odooUrl: String): String {
    return "${odooUrl}web/image?model=hr.employee.public&id=$userId&field=avatar_128"
}

fun getAvatarRequestHeaders(sessionId: String): List<AvatarRequestHeader> {
    return listOf(
        AvatarRequestHeader(
            name = "cookie",
            value = "session_id=$sessionId"
        )
    )
}