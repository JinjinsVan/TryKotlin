package my.code.trykotlin.business

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import my.code.trykotlin.R

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_input_new_password.*
import kotlinx.android.synthetic.main.layout_psw_edittext.view.*
import kotlinx.android.synthetic.main.layout_title.*
import my.code.trykotlin.http.ApiService
import my.code.trykotlin.http.RespBase

/**
 * 找回密码页面 or 修改密码页面
 *
 */
class InputNewPasswordActivity : AppCompatActivity() {
    private var isFirstEtSecretMode = true
    private var isSecondEtSecretMode = true
    private var isModifyPswPage: Boolean = false
    private val apiService by lazy { ApiService.create() }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_input_new_password)
        addThis(this)
        when (intent.getStringExtra(IntentKeynames.KEY_ACTIVITY_NAME)) {
            IntentKeynames.AFTER_MODIFY_PSW -> {
                isModifyPswPage = true
                tv_title_center.text = getString(my.code.trykotlin.R.string.change_password)
                psw_et_first.my_edit.hint = getString(my.code.trykotlin.R.string.input_old_password)
                psw_et_second.my_edit.hint = getString(my.code.trykotlin.R.string.input_new_password)
            }
            IntentKeynames.AFTER_FORGOT_PSW -> {
                isModifyPswPage = false
                tv_title_center.text = getString(my.code.trykotlin.R.string.title_new_psw)
                psw_et_first.my_edit.hint = getString(my.code.trykotlin.R.string.input_new_password)
                psw_et_second.my_edit.hint = getString(my.code.trykotlin.R.string.repeat_new_password)
            }
        }

        rl_title_back.setOnClickListener {
            onBackPressed()
        }
        tv_title_right.visibility = View.GONE

        psw_et_first.my_edit.afterTextChanged { checkInput(it) }
        psw_et_second.my_edit.afterTextChanged { checkInput(it) }

        btn_ensure.setOnClickListener {
            when (isModifyPswPage) {
                true -> {
                    val observable: Observable<RespBase> =
                            when (ApiService.isDummyData) {
                                true -> ApiService.requestLocalData(this, "RespBase.json", RespBase::class.java)
                                else -> apiService.modifyPsw(PreferenceUtils.getUserId(this), PreferenceUtils.getUsername(this),
                                        psw_et_first.my_edit.text.toString(), psw_et_second.my_edit.text.toString())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                            }

                    observable.subscribe(
                            { result ->
                                when (result.code) {
                                    200 -> {
                                        startActivity(Intent(this, LoginActivity::class.java)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        finish()
                                    }


                                    else -> showToast(result.msg)
                                }
                            },
                            { error -> showToast("error : " + error.localizedMessage) })
                }

                else -> {
                    if (psw_et_first.my_edit.text.toString() == psw_et_second.my_edit.text.toString()) {
                        val observable: Observable<RespBase> =
                                when (ApiService.isDummyData) {
                                    true -> ApiService.requestLocalData(this, "RespBase.json", RespBase::class.java)
                                    else -> apiService.resetPsw(PreferenceUtils.getUserId(this), psw_et_first.my_edit.text.toString(), psw_et_first.my_edit.text.toString())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                }

                        observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { result ->
                                            when (result.code) {
                                                200 -> {
                                                    startActivity(Intent(this, LoginActivity::class.java)
                                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                    finish()
                                                }

                                                else -> showToast(result.msg)
                                            }
                                        },
                                        { error -> showToast("error : " + error.localizedMessage) })
                    } else {
                        showToast(getString(my.code.trykotlin.R.string.prompt_psw_not_same))
                    }
                }

            }
        }


    }


    private fun checkInput(it: String) {

        when {
            psw_et_first.my_edit.text.isEmpty() or psw_et_first.my_edit.text.isEmpty() -> btn_ensure.isEnabled = false
            psw_et_second.my_edit.text.isNotEmpty() or psw_et_second.my_edit.text.isNotEmpty() -> btn_ensure.isEnabled = true
        }

    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        when (isModifyPswPage) {
            true -> {
                startActivity(Intent(this, HomeActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
            else -> finish()
        }
    }
}
