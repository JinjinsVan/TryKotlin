package my.code.trykotlin.http

import java.io.Serializable

/**
 * Created by Jins on 2018/4/20.
 */
object RespValidationItems {
    data class ValidationItems(
            val code: Int,
            val msg: String,
            val data: List<Data>
    ) : Serializable

    data class Data(
            val id: String,
            val name: String,
            val description: String,
            val order: String,
            val create_time: String

    ) : Serializable
}