package net.mindzone.robroo.injection

import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Alaa Moataz on 9/6/20.
 */

@Singleton
class AuthorizationInterceptor @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer " +
                            sharedPreferencesHelper.getCurrentIdToken()
                )
                .build()

        return chain.proceed(request)
    }
}