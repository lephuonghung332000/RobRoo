package net.mindzone.sampleaudit

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import net.mindzone.robroo.BuildConfig
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelperImp.Companion.PREF_USER_ID
import net.mindzone.robroo.utils.AuditType
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*

class AuditManager constructor() {

    private object HOLDER {
        val INSTANCE = AuditManager()
    }

    companion object {
        val instance: AuditManager by lazy { HOLDER.INSTANCE }
    }

    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    fun track(context: Context, type: AuditType, event: String, foreign_id: Int?, info: String?)
    {
        if (context == null) return;
        Thread {
            try {
                val prefs: SharedPreferences = context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
                val azureId = prefs.getString(PREF_USER_ID, "") ?: ""

                val url = URL(BuildConfig.BASE_URL + "audit")
                var user = BuildConfig.BASE_USER + ":" + BuildConfig.BASE_PASS

                val version_os = Build.VERSION.RELEASE
                val version_app = BuildConfig.VERSION_NAME
                val model = getDeviceName()
                val manufacturer = Build.MANUFACTURER

                var reqParam = URLEncoder.encode("azure_id", "UTF-8") + "=" + URLEncoder.encode(azureId, "UTF-8")
                reqParam += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type.valueName, "UTF-8")
                reqParam += "&" + URLEncoder.encode("os", "UTF-8") + "=" + URLEncoder.encode("ANDROID", "UTF-8")
                reqParam += "&" + URLEncoder.encode("event", "UTF-8") + "=" + URLEncoder.encode(event, "UTF-8")
                if (info != null) reqParam += "&" + URLEncoder.encode("information", "UTF-8") + "=" + URLEncoder.encode(info, "UTF-8")
                reqParam += "&" + URLEncoder.encode("foreign_id", "UTF-8") + "=" + URLEncoder.encode(foreign_id.toString(), "UTF-8")
                reqParam += "&" + URLEncoder.encode("device", "UTF-8") + "=" + URLEncoder.encode(manufacturer, "UTF-8")
                reqParam += "&" + URLEncoder.encode("model", "UTF-8") + "=" + URLEncoder.encode(model, "UTF-8")
                reqParam += "&" + URLEncoder.encode("version_os", "UTF-8") + "=" + URLEncoder.encode(version_os, "UTF-8")
                reqParam += "&" + URLEncoder.encode("version_app", "UTF-8") + "=" + URLEncoder.encode(version_app, "UTF-8")


                var encodedCredentials = ""
                if (Build.VERSION.SDK_INT >= 26) {
                    encodedCredentials = Base64.getEncoder().encode(user.toByteArray()).toString();
                } else {
                    encodedCredentials = android.util.Base64.encodeToString(user.toByteArray(), 0)
                }

                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.setRequestProperty("Authorization", "Basic $encodedCredentials")
                httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
                httpURLConnection.doInput = true
                httpURLConnection.doOutput = true

                val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
                outputStreamWriter.write(reqParam)
                outputStreamWriter.flush()

                // Check if the connection is successful
                val responseCode = httpURLConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = httpURLConnection.inputStream.bufferedReader()
                        .use { it.readText() }  // defaults to UTF-8
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }.start()
    }
}