package my.code.trykotlin.http

/**
 * Created by Jins on 2018/4/17.
 */

data class RespLogin(
		val code: Int,
		val msg: String,
		val user_id: String,
		val username: String
)