package odoo.miem.android.core.dataStore.impl.preferences

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * General internal extension for manipulation with Shared Preferences
 *
 * Usage:
 * val someValue by preferences.delegates.*
 *
 * @author Vorozhtsov Mikhail
 */
internal val SharedPreferences.delegates get() = SharedPreferenceDelegates(this)

/**
 * [SharedPreferenceDelegates] is a special class for simple delegation as property
 *
 * Usage:
 * val someValue by preferences.delegates.string()
 *
 * After that, we will put value someValue with key someValue, and yes, we also
 * can put someValue with other key - just push it in `key` function propertyЖ
 * val someValue by preferences.delegates.string(key = "SomeCoolKey")
 *
 * Same about default value
 *
 * @author Vorozhtsov Mikhail
 */
internal class SharedPreferenceDelegates(private val prefs: SharedPreferences) {
    fun boolean(default: Boolean = false, key: String? = null): ReadWriteProperty<Any, Boolean> =
        create(default, key, prefs::getBoolean, prefs.edit()::putBoolean)

    fun int(default: Int = 0, key: String? = null): ReadWriteProperty<Any, Int> =
        create(default, key, prefs::getInt, prefs.edit()::putInt)

    fun float(default: Float = 0f, key: String? = null): ReadWriteProperty<Any, Float> =
        create(default, key, prefs::getFloat, prefs.edit()::putFloat)

    fun long(default: Long = 0L, key: String? = null): ReadWriteProperty<Any, Long> =
        create(default, key, prefs::getLong, prefs.edit()::putLong)

    fun string(default: String = "", key: String? = null): ReadWriteProperty<Any, String> =
        create(
            default,
            key,
            { k, d -> prefs.getString(k, d) as String },
            prefs.edit()::putString
        )

    fun stringSet(default: Set<String> = emptySet(), key: String? = null): ReadWriteProperty<Any, Set<String>> =
        create(
            default,
            key,
            { k, d -> prefs.getStringSet(k, d) as Set<String> },
            prefs.edit()::putStringSet
        )

    private fun <T> create(
        default: T,
        key: String? = null,
        getter: (key: String, default: T) -> T,
        setter: (key: String, value: T) -> SharedPreferences.Editor
    ) = object : ReadWriteProperty<Any, T> {
        private fun key(property: KProperty<*>) = key ?: property.name

        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key(property), default)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
            setter(key(property), value).apply()
    }
}
