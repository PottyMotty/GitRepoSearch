package com.pottymotty.gitreposearch.data.serialization

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.io.IOException
import java.time.Instant


class InstantAdapter : JsonAdapter<Instant>() {

    @FromJson
    override fun fromJson(reader: JsonReader): Instant {
        val string = reader.nextString()
        return try {
            Instant.parse(string)
        } catch (e: Exception) {
            throw IOException("Invalid Instant: $string", e)
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Instant?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }
}