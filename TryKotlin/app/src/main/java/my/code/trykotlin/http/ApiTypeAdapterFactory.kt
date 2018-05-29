package my.code.trykotlin.http

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * Created by Jins on 2018/4/25.
 */
class ApiTypeAdapterFactory : TypeAdapterFactory {

    lateinit var dataElementName: String

    constructor(dataElementName: String) {
        this.dataElementName = dataElementName
    }


    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
        val delegate = gson!!.getDelegateAdapter(this, type)
        val elementTypeAdapter = gson!!.getAdapter(JsonElement::class.java)


        return object : TypeAdapter<T>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: T) {
                delegate!!.write(out, value)
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): T {
                var jsonElement = elementTypeAdapter.read(`in`)
                if (jsonElement.isJsonObject && !jsonElement.isJsonArray) {
                    val jsonObject = jsonElement.asJsonObject
                    if (jsonObject.has("code")) {
                        val status = jsonObject.get("code").asInt
                        val message = jsonObject.get("msg").asString

//                        if (status != 200)
//
//                            throw ApiException(status, message)

                    }
                }
                return delegate!!.fromJsonTree(jsonElement)
            }

        }.nullSafe()
    }
}
