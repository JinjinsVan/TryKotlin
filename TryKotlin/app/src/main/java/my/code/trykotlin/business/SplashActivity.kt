package my.code.trykotlin.business

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import my.code.trykotlin.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(my.code.trykotlin.R.layout.activity_splash)
        addThis(this)
        object : CountDownTimer(1500, 1000) {
            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                overridePendingTransition(my.code.trykotlin.R.anim.fade_in, my.code.trykotlin.R.anim.fade_out)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }.start()
    }
}
