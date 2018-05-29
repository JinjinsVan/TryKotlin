package my.code.trykotlin.http

import java.io.Serializable

/**
 * Created by Jins on 2018/4/20.
 */
object RespRobotInfo {

    data class RobotInfo(
            val code: Int,
            val msg: String,
            val data: Data
    ) : Serializable

    data class Data(
            val id: String,
            val business_code: String,
            val item_list: List<ItemData>
    ) : Serializable

    data class ItemData(
            val id: String = "",
            val name: String = "",
            val description: String = "",
            val order: String = "",
            val create_time: String = ""
    ) : Serializable
}