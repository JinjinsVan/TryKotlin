package my.code.trykotlin.business

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import my.code.trykotlin.R
import my.code.trykotlin.common.JUtils
import my.code.trykotlin.http.ApiService
import my.code.trykotlin.http.RespSendCode
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.layout_title.*
import java.util.concurrent.TimeUnit

class ForgotPasswordActivity : AppCompatActivity() {

    private val apiService by lazy { ApiService.create() }
    private var disposable: Disposable? = null
    private var receivedCode: Int? = null

    private val timer = object : CountDownTimer(60000, 1000) {

        override fun onFinish() {
            tv_send_code.isEnabled = true
            tv_send_code.isClickable = true
            tv_send_code.text = getString(my.code.trykotlin.R.string.send_sms_code)
        }

        override fun onTick(millisUntilFinished: Long) {
            tv_send_code.isEnabled = false
            tv_send_code.isClickable = false
            tv_send_code.text = "已发送(" + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + "s)"
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_forgot_password)
        addThis(this)
        tv_title_center.text = getString(my.code.trykotlin.R.string.title_forgot_psw)

        rl_title_back.setOnClickListener { finish() }
        rl_title_back.visibility = View.VISIBLE

        tv_title_right.visibility = View.GONE

        et_username.afterTextChanged { checkInput(it) }
        et_code.afterTextChanged { checkInput(it) }

        tv_send_code.setOnClickListener {
            when {
                et_username.text.toString().isBlank() -> showToast(getString(my.code.trykotlin.R.string.prompt_username_cannot_be_null))
                !JUtils.isMobileNo(et_username.text.toString()) -> showToast(getString(my.code.trykotlin.R.string.prompt_phone_num_invalid))
                else -> sendCode(et_username.text.toString())
            }
        }

        btn_next.setOnClickListener { next() }

    }

    private fun checkInput(it: String) {
        when {
            et_username.text.isEmpty() or et_code.text.isEmpty() -> btn_next.isEnabled = false
            et_username.text.isNotEmpty() or et_code.text.isNotEmpty() -> btn_next.isEnabled = true
        }
    }


    private fun next() {
        val codeInput = et_code.text.toString()

        when (codeInput == receivedCode.toString()) {
            true -> startActivity(Intent(this, InputNewPasswordActivity::class.java)
                    .putExtra(IntentKeynames.KEY_ACTIVITY_NAME, IntentKeynames.AFTER_FORGOT_PSW))
            false -> showToast(getString(my.code.trykotlin.R.string.prompt_code_error))
        }
    }


    private fun sendCode(username: String) {

        timer.start()

        val observable: Observable<RespSendCode> =
                when (ApiService.isDummyData) {
                    true -> ApiService.requestLocalData(this, "RespSendCode.json", RespSendCode::class.java)
                    else -> apiService.sendCode(username)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }

        disposable = observable.subscribe(
                { result ->
                    when (result.code) {
                        200 -> receivedCode = result.verification_code
                        else -> showToast(result.msg)
                    }
                },
                { error -> showToast("error : " + error.localizedMessage) }
        )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

}
