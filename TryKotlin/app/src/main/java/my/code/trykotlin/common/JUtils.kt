package my.code.trykotlin.common

import java.util.regex.Pattern

/**
 * Created by Jins on 2018/5/9.
 */
class JUtils {
    companion object {

        fun isMobileNo(mobiles: String): Boolean {
            val p = Pattern.compile("^1[0123456789]\\d{9}$")
            val m = p.matcher(mobiles)
            println(m.matches().toString() + "---")
            return m.matches()
        }
    }
}