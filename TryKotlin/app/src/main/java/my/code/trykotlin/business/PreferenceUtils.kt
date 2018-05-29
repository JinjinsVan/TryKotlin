package my.code.trykotlin.business

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity

/**
 * Created by Jins on 2018/4/16.
 */
class PreferenceUtils {


    companion object {
        lateinit var sharePreferences: SharedPreferences
        private val SP_NAME: String = "robot_validation"
        private val USER_NAME: String = "username"
        private val USER_ID: String = "user_id"
        private val PASSWORD: String = "password"

        fun saveUsername(cxt: AppCompatActivity, value: String) {
            saveString(cxt, USER_NAME, value)
        }

        fun getUsername(cxt: AppCompatActivity): String {
            return getString(cxt, USER_NAME)
        }

        fun saveUserId(cxt: AppCompatActivity, value: String) {
            saveString(cxt, USER_ID, value)
        }

        fun getUserId(cxt: AppCompatActivity): String {
            return getString(cxt, USER_ID)
        }

        fun savePassword(cxt: AppCompatActivity, value: String) {
            saveString(cxt, PASSWORD, value)
        }

        fun getPassword(cxt: AppCompatActivity): String {
            return getString(cxt, PASSWORD)
        }


        private fun saveString(cxt: AppCompatActivity, key: String, value: String) {
            sharePreferences = cxt.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            sharePreferences.edit().putString(key, value).apply()

        }

        private fun getString(cxt: AppCompatActivity, key: String): String {
            sharePreferences = cxt.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            return sharePreferences.getString(key, "")

        }

        private fun saveBoolean(cxt: AppCompatActivity, key: String, value: Boolean) {
            sharePreferences = cxt.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            sharePreferences.edit().putBoolean(key, value).apply()

        }


        private fun getBoolean(cxt: AppCompatActivity, key: String): Boolean {
            sharePreferences = cxt.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            return sharePreferences.getBoolean(key, false)
        }
    }
}