package my.code.trykotlin.business

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import my.code.trykotlin.R
import my.code.trykotlin.http.RespRobotInfo
import kotlinx.android.synthetic.main.activity_input_code_by_hand.*
import kotlinx.android.synthetic.main.layout_title.*

/**
 * 手工输入
 *
 */
class InputCodeByHandActivity : AppCompatActivity() {


    private var scanType: String? = null
    private var robotInfo: RespRobotInfo.Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_input_code_by_hand)
        addThis(this)
        scanType = intent.getStringExtra(IntentKeynames.KEY_SCAN_TYPE)

        tv_title_center.text = getString(my.code.trykotlin.R.string.title_input_by_hand)
        rl_title_back.visibility = View.VISIBLE
        tv_title_right.visibility = View.GONE

        rl_title_back.setOnClickListener { finish() }


        tv_code_title.text = getString(my.code.trykotlin.R.string.input_type_code)
        et_robot_code.hint = getString(my.code.trykotlin.R.string.hint_type_code)


        rl_title_back.setOnClickListener { finish() }

        et_robot_code.setOnTouchListener { _, event ->
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_DOWN) {
                if (et_robot_code.text.isNotEmpty()) {
                    if ((event.rawX >= et_robot_code.right - et_robot_code.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                        et_robot_code.setText("")
                    }
                }
            }

            false
        }
        et_robot_code.afterTextChanged { checkInput(it) }

        btn_ensure.setOnClickListener {


            startActivity(Intent(this, LoadingProcessRobotInfoActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra(IntentKeynames.KEY_ROBOT_CODE, et_robot_code.text.toString()))

        }

    }


    private fun checkInput(it: String) {

        when (it.isEmpty()) {
            true -> {
                et_robot_code.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
                btn_ensure.isEnabled = false
            }
            else -> {
                et_robot_code.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, my.code.trykotlin.R.mipmap.icon_clean, 0)
                btn_ensure.isEnabled = true
            }
        }

    }


}
