package my.code.trykotlin.business

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import my.code.trykotlin.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_title.*

/**
 * 首页
 *
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_home)
        addThis(this)
        tv_title_center.text = getString(my.code.trykotlin.R.string.title_home)
        rl_title_back.visibility = View.GONE
        tv_title_right.text = getString(my.code.trykotlin.R.string.change_password)

        tv_title_right.setOnClickListener {
            startActivity(Intent(this, InputNewPasswordActivity::class.java)
                    .putExtra(IntentKeynames.KEY_ACTIVITY_NAME, IntentKeynames.AFTER_MODIFY_PSW))
            finish()
        }
        btn_start.setOnClickListener {
            startActivity(Intent(this, ScanBarcodeActivity::class.java)
                    .putExtra(IntentKeynames.KEY_SCAN_TYPE, IntentKeynames.TYPE_ROBOT_CODE))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAll(this)
    }

}
