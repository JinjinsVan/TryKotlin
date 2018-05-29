package my.code.trykotlin.business

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import my.code.trykotlin.R
import kotlinx.android.synthetic.main.activity_et_dialog.*

class EtDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_et_dialog)
        addThis(this)
        tv_dialog_cancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        tv_dialog_ensure.setOnClickListener {
            when (et_dialog_content.text.isEmpty()) {
                true -> showToast("请输入")
                false -> {
                    setResult(Activity.RESULT_OK, Intent().putExtra(IntentKeynames.KEY_PROBLEM_DESC, et_dialog_content.text.toString()))
                    finish()
                }
            }
        }
    }
}
