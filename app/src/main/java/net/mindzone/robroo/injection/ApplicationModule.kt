package net.mindzone.robroo.injection

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.BuildConfig
import net.mindzone.robroo.R
import net.mindzone.robroo.data.sharedpref.sharePreferencesComment.SharedPreferencesCommentHelper
import net.mindzone.robroo.data.sharedpref.sharePreferencesComment.SharedPreferencesCommentHelperImp
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelperImp
import net.mindzone.robroo.data.utils.ApiClient
import net.mindzone.robroo.data.utils.AppDatabase
import net.mindzone.robroo.utils.Constants
import net.mindzone.robroo.utils.NetworkUtils
import net.mindzone.robroo.utils.NoConnectionException
import net.mindzone.sampleaudit.AuditManager
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL
    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(sharedPreferencesHelper: SharedPreferencesHelper) =
        AuthorizationInterceptor(
            sharedPreferencesHelper
        )


    @Provides
    @Singleton
    fun provideApiClient(
        BASE_URL: String,
        authorizationInterceptor: AuthorizationInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): ApiClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                // A zero value means no timeout at all.
                // This is enforced because our client isn't able to send/receive a request body/response body always to/from the server within the defined timeout.
                // This guarantees that whatever the size of the image being uploaded is, it won't get `SocketTimeoutException` unless the server itself has issues.
                .writeTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authorizationInterceptor)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context, prefs: SharedPreferencesHelper): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .cache(Cache(context.cacheDir, CACHE_SIZE.toLong()))
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor {
                if (!NetworkUtils.isConnected(context))
                    throw NoConnectionException(
                        context.getString(R.string.msg_no_network)
                    )
                val request = it.request()
                val builder = request.newBuilder()
                builder.addHeader("X-localization", "en")
                    .addHeader("Content-Type", "application/json")

                if (prefs.currentUserId.value != null){
                    builder.addHeader(
                        "Authorization",
                        "${Constants.KEY_BEARER} ${prefs.currentUserId.value}"
                    )
                }

                it.proceed(builder.build())

            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    @Provides
    @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, AppDatabase.DB_NAME
    )/*.addMigrations(CounterBookDataBase.MIGRATION_1_2, CounterBookDataBase.MIGRATION_2_3)*/
        .build()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(sharedPreferences: SharedPreferences): SharedPreferencesHelper =
        SharedPreferencesHelperImp(
            sharedPreferences
        )

    @Provides
    @Singleton
    fun sharedPreferencesCommentHelper(sharedPreferences: SharedPreferences) : SharedPreferencesCommentHelper = SharedPreferencesCommentHelperImp(sharedPreferences)
    companion object {
        const val CACHE_SIZE = 10 * 1024 * 1024
        const val CONNECT_TIMEOUT = 1 * 60 // seconds
        const val READ_TIMEOUT = 1 * 60 // ms
        const val WRITE_TIMEOUT = 1 * 60 // ms
    }

}