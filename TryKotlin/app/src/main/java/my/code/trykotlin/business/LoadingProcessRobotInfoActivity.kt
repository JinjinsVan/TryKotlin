package my.code.trykotlin.business

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import my.code.trykotlin.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.code.trykotlin.http.ApiService
import my.code.trykotlin.http.RespRobotInfo
import java.io.Serializable

/**
 * 可以在此loading页面做一些业务处理，由扫码和手动输入两个入口页面可以进入
 */
class LoadingProcessRobotInfoActivity : AppCompatActivity() {
    private val apiService by lazy { ApiService.create() }
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_loading_processing_robot_info)
        addThis(this)
//        Glide.with(this)
//                .asGif()
//                .load(R.mipmap.loading)
//                .into(iv_loading)

        val code = intent.getStringExtra(IntentKeynames.KEY_ROBOT_CODE)

        val observable: Observable<RespRobotInfo.RobotInfo> =
                when (ApiService.isDummyData) {
                    true -> ApiService.requestLocalData(this, "RespRobotInfo.json", RespRobotInfo.RobotInfo::class.java)
                    else -> apiService.robotInfo(PreferenceUtils.getUserId(this), code)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }

        disposable = observable.subscribe(
                { result ->
                    when (result.code) {
                    //正常数据
                        200 -> {
                            intent = Intent(this, RobotInfoActivity::class.java)
                                    .putExtra(IntentKeynames.KEY_ROBOT_INFO, result.data as Serializable)
                            startActivity(intent)
                            finish()

                        }

                        405 -> {
                            showCodeInvalidToast(my.code.trykotlin.R.id.fl_root)
                            setResult(Activity.RESULT_CANCELED)
                            finish()
                        }

                        else -> {
                            showToast(result.msg)
                            setResult(Activity.RESULT_CANCELED)
                            finish()
                        }
                    }
                },
                { error ->
                    run {
                        showToast(error.localizedMessage)
                        finish()
                    }
                }
        )
    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
