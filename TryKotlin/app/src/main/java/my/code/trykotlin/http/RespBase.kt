package my.code.trykotlin.http

/**
 * Created by Jins on 2018/4/17.
 */

data class RespBase(
        val code: Int,
        val msg: String,
        val status: Int
)