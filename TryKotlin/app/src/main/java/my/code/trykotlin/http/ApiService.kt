package my.code.trykotlin.http

import android.content.Context
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.reflect.Type


/**
 *
 * Created by Jins on 2018/4/13.
 */
interface ApiService {

    companion object {
        //set this True,request for local json data(/assets/)
        val isDummyData: Boolean = true
        private val BASE_URL: String = "your release server url"

        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(ApiService::class.java)

        }

        fun <T> requestLocalData(ctx: Context, assetsFileName: String, type: Type): Observable<T> {
            val content = ctx.assets.open(assetsFileName).reader(Charsets.UTF_8).readText()
            return Observable.just(assetsFileName).map { _ ->
                GsonBuilder().registerTypeAdapterFactory(ApiTypeAdapterFactory("data")).create()
                        .fromJson<T>(content, type)
            }
        }
    }

    //发送短信验证码
    @GET("/Home/Index/sendVerCode")
    fun sendCode(@Query("mobile") mobile: String):
            Observable<RespSendCode>

    //用户登录
    @GET("/Mobile/Index/login")
    fun login(@Query("username") username: String,
              @Query("password") password: String):
            Observable<RespLogin>

    //修改密码
    @FormUrlEncoded
    @POST("/Mobile/User/modifyPassword")
    fun modifyPsw(
            @Field("user_id") user_id: String,
            @Field("username") username: String,
            @Field("password") oldpsw: String,
            @Field("new_password") newpsw: String):
            Observable<RespBase>

    //重新设置密码
    @FormUrlEncoded
    @POST("/Mobile/User/resetPassword")
    fun resetPsw(@Field("user_id") user_id: String,
                 @Field("password") password: String,
                 @Field("confirm_password") confirm_password: String):
            Observable<RespBase>

    //录入条码
    @GET("/Mobile/Report/code")
    fun robotInfo(@Query("user_id") user_id: String,
                  @Query("code") code: String):
            Observable<RespRobotInfo.RobotInfo>


}


