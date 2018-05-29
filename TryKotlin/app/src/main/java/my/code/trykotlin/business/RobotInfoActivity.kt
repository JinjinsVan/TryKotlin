package my.code.trykotlin.business

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import my.code.trykotlin.R
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_robot_info.*
import kotlinx.android.synthetic.main.layout_title.*
import my.code.trykotlin.http.ApiService
import my.code.trykotlin.http.RespRobotInfo

class RobotInfoActivity : AppCompatActivity() {

    private val apiService by lazy { ApiService.create() }
    private var disposable: Disposable? = null
    private var robotInfo: RespRobotInfo.Data? = null
    private var validationItems: List<RespRobotInfo.ItemData>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_robot_info)
        addThis(this)
        tv_title_center.text = getString(my.code.trykotlin.R.string.title_content)
        rl_title_back.visibility = View.VISIBLE
        tv_title_right.visibility = View.GONE

        rl_title_back.setOnClickListener { onBackPressed() }

        robotInfo = intent.getSerializableExtra(IntentKeynames.KEY_ROBOT_INFO) as? RespRobotInfo.Data
        validationItems = robotInfo!!.item_list

        validationItems = validationItems!!.sortedBy { it.order.toInt() }
        tv_robot_unqualified_items.visibility = View.VISIBLE
        tv_robot_unqualified_items_content.visibility = View.VISIBLE
        btn_start_validation.visibility = View.VISIBLE
        val sbItems = StringBuilder()
        validationItems!!.forEach { tv_robot_unqualified_items_content.text = sbItems.append(it.description).append("\n").toString() }

        btn_start_validation.setOnClickListener {
            //do sth.
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
