package my.code.trykotlin.http

/**
 * Created by Jins on 2018/4/17.
 */

data class RespSendCode(
		val code: Int,
		val msg: String,
		val verification_code: Int
)