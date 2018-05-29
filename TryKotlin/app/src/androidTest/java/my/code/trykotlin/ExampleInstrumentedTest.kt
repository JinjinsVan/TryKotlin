package my.code.trykotlin

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.code.trykotlin.http.ApiService
import com.code.trykotlin.http.RespValidationItems
import io.reactivex.Observable

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.itcrm.robotvalidation", appContext.packageName)
        val observable: Observable<RespValidationItems.ValidationItems> = ApiService.requestLocalData(appContext, "RespBase.json", RespValidationItems.ValidationItems::class.java)

        observable.subscribe({ result ->
            if (result.code == 200) {
                val msg = result.msg
            }

        })

    }
}
