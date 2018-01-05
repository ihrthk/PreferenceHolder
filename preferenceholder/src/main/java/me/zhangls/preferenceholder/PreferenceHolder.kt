package me.zhangls.preferenceholder

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

abstract class PreferenceHolder {

    protected inline fun <reified T : Any> bindToPreferenceField(default: T)
            : ReadWriteProperty<PreferenceHolder, T>
            = PreferenceFieldBinder(null, T::class, default)

    protected inline fun <reified T : Any> bindToPreferenceField(key: String? = null, default: T)
            : ReadWriteProperty<PreferenceHolder, T>
            = PreferenceFieldBinder(key, T::class, default)


    companion object {
        /**
         *  It should be used to set ApplicationContext on project Application class. Only case when
         *  it could be ommitted is when testingMode is turned on.
         */
        fun setContext(context: Context) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context)
        }

        private const val noPreferencesSetExceptionText = "No preferences in PreferenceHolder instance. " +
                "Add in Application class PreferenceHolder.setContext(applicationContext) "

        private var preferences: SharedPreferences? = null

        internal fun getPreferencesOrThrowException(): SharedPreferences = PreferenceHolder.preferences
                ?: throw Exception(noPreferencesSetExceptionText)
    }
}

class PreferenceFieldBinder<T : Any>(
        private val key: String?,
        private val clazz: KClass<T>,
        private val default: T
) : ReadWriteProperty<PreferenceHolder, T> {

    fun getKey(key: String?, property: KProperty<*>) = key ?: "${property.name}Key"


    override operator fun getValue(thisRef: PreferenceHolder, property: KProperty<*>): T {
        val pref = PreferenceHolder.getPreferencesOrThrowException()
        val newKey = getKey(key, property)

        return pref.run {
            when (clazz) {
                Long::class -> getLong(newKey, default as Long) as T
                Int::class -> getInt(newKey, default as Int) as T
                String::class -> getString(newKey, default as String) as T
                Boolean::class -> getBoolean(newKey, default as Boolean) as T
                Float::class -> getFloat(newKey, default as Float) as T
                else -> throw Exception("PreferenceHolder don't support $clazz")
            }
        }
    }

    override fun setValue(thisRef: PreferenceHolder, property: KProperty<*>, value: T) {
        val pref = PreferenceHolder.getPreferencesOrThrowException()
        val newKey = getKey(key, property)

        pref.edit().apply {
            when (clazz) {
                Long::class -> putLong(newKey, value as Long)
                Int::class -> putInt(newKey, value as Int)
                String::class -> putString(newKey, value as String)
                Boolean::class -> putBoolean(newKey, value as Boolean)
                Float::class -> putFloat(newKey, value as Float)
                else -> throw Exception("PreferenceHolder don't support $clazz")
            }
        }.apply()
    }

}


