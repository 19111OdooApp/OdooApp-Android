package odoo.miem.android.core.jsonrpc.parser.api.di.annotation

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class SpecifiedTypeOrNull(val token: JsonReader.Token) {

    class Adapter(
        private val delegate: JsonAdapter<Any?>,
        private val token: JsonReader.Token
    ) : JsonAdapter<Any?>() {
        override fun fromJson(reader: JsonReader): Any? {
            if (reader.peek() != token) {
                reader.skipValue()
                return null
            }
            return delegate.fromJson(reader)
        }

        override fun toJson(writer: JsonWriter, value: Any?) {
            delegate.toJson(writer, value)
        }

        class Factory : JsonAdapter.Factory {
            override fun create(
                type: Type,
                annotations: Set<Annotation>,
                moshi: Moshi
            ): JsonAdapter<*>? {
                if (annotations.isEmpty()) {
                    return null
                }
                var token: JsonReader.Token? = null
                var nextAnnotations: MutableSet<Annotation> = mutableSetOf()
                for (annotation in annotations) {
                    if (annotation is SpecifiedTypeOrNull) {
                        token = annotation.token
                        nextAnnotations = annotations.toMutableSet()
                        nextAnnotations -= annotation
                    }
                }
                if (token == null) {
                    return null
                }
                val delegate = moshi.nextAdapter<Any?>(this, type, nextAnnotations)
                return Adapter(delegate, token)
            }
        }
    }
}
