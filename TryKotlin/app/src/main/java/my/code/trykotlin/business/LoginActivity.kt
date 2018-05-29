package my.code.trykotlin.business


import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.MotionEvent
import android.view.View
import my.code.trykotlin.R
import io.reactivex.Observable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_title.*
import my.code.trykotlin.http.ApiService
import my.code.trykotlin.http.RespLogin

/**
 * 登录页面
 *
 */
class LoginActivity : AppCompatActivity(), DialogEtValueCallback {


    private val apiService by lazy { ApiService.create() }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_login)
        addThis(this)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 0)
        }

        tv_title_center.text =getString(my.code.trykotlin.R.string.title_login)
        rl_title_back.visibility = View.GONE
        tv_title_right.visibility = View.GONE

        et_username.afterTextChanged { checkInput(it) }
        et_password.afterTextChanged { checkInput(it) }

        //thanks to "https://stackoverflow.com/questions/13135447/setting-onclicklistner-for-the-drawable-right-of-an-edittext/26269435#26269435", AZ_ 's solution,amazing!
        et_username.setOnTouchListener { _, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_DOWN) {
                if (et_username.text.isNotEmpty()) {
                    if ((event.rawX >= et_username.right - et_username.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                        et_username.setText("")
                    }
                }
            }

            false
        }


        btn_login.setOnClickListener {
            login(et_username.text.toString(), et_password.text.toString())
        }

        tv_forgot_psw.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

    }

    private fun checkInput(it: String) {
        if (et_username.text.isEmpty()) {
            et_username.setCompoundDrawablesRelativeWithIntrinsicBounds(my.code.trykotlin.R.mipmap.icon_user, 0, 0, 0)
        } else {
            et_username.setCompoundDrawablesRelativeWithIntrinsicBounds(my.code.trykotlin.R.mipmap.icon_user, 0, my.code.trykotlin.R.mipmap.icon_clean, 0)
        }

        when {
            et_username.text.isEmpty() or et_password.text.isEmpty() -> btn_login.isEnabled = false
            et_username.text.isNotEmpty() or et_password.text.isNotEmpty() -> btn_login.isEnabled = true
        }
    }


    private fun login(username: String, password: String) {

        val observable: Observable<RespLogin> = when (ApiService.isDummyData) {
            true -> ApiService.requestLocalData(this, "RespLogin.json", RespLogin::class.java)
            else -> apiService.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        disposable = observable.subscribe(
                { result ->
                    when (result.code) {
                        200 -> {
                            PreferenceUtils.saveUserId(this,result.user_id)
                            PreferenceUtils.saveUsername(this, username)
                            PreferenceUtils.savePassword(this, password)
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }
                        else -> {
                           showToast(result.msg)
                        }
                    }
                },
                { error -> showToast("error : " + error.localizedMessage+ "\n" + this.localClassName) }
        )


    }

    override fun dialogEtValueBack(value: String) {
        showToast(value)
    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAll(this)
    }


}








