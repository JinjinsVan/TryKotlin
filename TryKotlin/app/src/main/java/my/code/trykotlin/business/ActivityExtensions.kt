package my.code.trykotlin.business


import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import my.code.trykotlin.R


/**
 * Created by Jins on 2018/4/11.
 */


fun AppCompatActivity.showToast(text: String) {

    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showCodeInvalidToast(containerId: Int) {
    val inflater = LayoutInflater.from(this)
//    val container: ViewGroup = findViewById(containerId)
    val layout: View = inflater.inflate(my.code.trykotlin.R.layout.layout_custom_toast, null)
    with(Toast(applicationContext)) {
        setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}

fun addThis(activity: AppCompatActivity) {
    (activity.application as BaseApplication).add(activity)

}


fun finishAll(activity: AppCompatActivity) {
    (activity.application as BaseApplication).finishAll()

}

interface DialogEtValueCallback {
    fun dialogEtValueBack(value: String)
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    })


}


