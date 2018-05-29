package my.code.trykotlin.business

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import my.code.trykotlin.R
import kotlinx.android.synthetic.main.layout_psw_edittext.view.*

/**
 * Created by Jins on 2018/4/28.
 */
class PswEditText @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    init {
        LayoutInflater.from(context).inflate(my.code.trykotlin.R.layout.layout_psw_edittext, this, true)
        this.my_toggle.visibility = View.GONE
        this.my_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isBlank()) {
                    my_toggle.visibility = View.GONE
                } else {
                    my_toggle.visibility = View.VISIBLE
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })


        this.my_toggle.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    my_edit.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    my_edit.setSelection(my_edit.text.length)
                }
                else -> {
                    my_edit.transformationMethod = PasswordTransformationMethod.getInstance()
                    my_edit.setSelection(my_edit.text.length)
                }
            }
        }
    }
}