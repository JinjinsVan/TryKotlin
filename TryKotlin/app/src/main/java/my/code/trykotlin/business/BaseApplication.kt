package my.code.trykotlin.business

import android.app.Application
import android.support.v7.app.AppCompatActivity
import java.util.ArrayList

/**
 * Created by Jins on 2018/5/14.
 */
class BaseApplication : Application() {
    private var mActivityList: MutableList<AppCompatActivity>? = null

    override fun onCreate() {
        super.onCreate()
    }

    fun add(baseActivity: AppCompatActivity) {
        if (mActivityList == null) {
            mActivityList = ArrayList<AppCompatActivity>()
        }
        mActivityList!!.add(baseActivity)
    }

    fun remove(baseActivity: AppCompatActivity) {
        if (mActivityList == null) {
            mActivityList = ArrayList<AppCompatActivity>()
        }
        mActivityList!!.remove(baseActivity)
    }

    fun finishAll() {
        for (i in mActivityList!!.indices) {
            mActivityList!!.get(i).finish()
        }
        mActivityList!!.clear()
    }
}