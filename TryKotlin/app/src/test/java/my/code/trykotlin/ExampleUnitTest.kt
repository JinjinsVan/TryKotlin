package my.code.trykotlin

import com.code.trykotlin.business.ValidateItemStatus
import com.code.trykotlin.http.RespValidationItems
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        var list: ArrayList<RespValidationItems.Data> = java.util.ArrayList()
        (4..10).mapTo(list) { RespValidationItems.Data("id" + it, "name" + it, "desc" + it, "cateId" + it, "typeid", it.toString(), "time", "2", "") }

        list.add(RespValidationItems.Data("id", "2", "desc", "cateId", "typeid", "2", "time", "1", ""))
        list.add(RespValidationItems.Data("id", "1", "desc", "cateId", "typeid", "1", "time", "2", ""))

        val newList = list.sortedBy { it.order.toInt() }

        print(newList.filterNot { it._validationStatus != ValidateItemStatus.QUALIFIED.key }.size)

    }
}
