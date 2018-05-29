package my.code.trykotlin.business

/**
 * Created by Jins on 2018/4/23.
 */
enum class RobotStatus(val key: String, val value: String) {
    UNVALIDATED("1", "未质检"), VALIDATING("2", "质检中"), QUALIFIED("3", "合格"), UNQUALIFIED("4", "不合格"),
    REPAIRED("5", "已修复"), REPAIRED_QUALIFIED("6", "已合格")
}
