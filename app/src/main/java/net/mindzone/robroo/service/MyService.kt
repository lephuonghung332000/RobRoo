package net.mindzone.robroo.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import net.mindzone.robroo.ui.main.MainActivity
import okhttp3.*
import java.io.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class MyService : Service() {
    private var binder: IBinder? = null
    var fileName = ""
    var docname:String=""
    var obj:Items?=null
    val listLoading= arrayListOf<String>()
    var azureid=MainActivity.user?.azureoid!!
    val client = OkHttpClient.Builder()
        .connectTimeout(0, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.SECONDS)
        .writeTimeout(0, TimeUnit.SECONDS)
        .addNetworkInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Connection", "close").build()
            chain.proceed(request)
        }
        .build()
    val executor = Executors.newFixedThreadPool(8)
    inner class MyBinder : Binder() {
        val service: MyService
            get() = this@MyService
    }
    override fun onCreate() {
        super.onCreate()
        binder = MyBinder()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        obj= intent?.getSerializableExtra("item") as Items?
        docname= obj?.docid.toString()+obj?.docnumber + obj?.productmodelcode
        fileName= "/" +docname + ".pdf"
        listLoading.add(docname)
         obj?.let {
            DownloadFileFromURL(it).executeOnExecutor(
                executor,it.downloadurl)
        }
        return START_NOT_STICKY
    }
    private fun publishSuccess(docNumber: String,fullPath:String) {
        val i = Intent("message_success")
        i.putExtra("docNumber", docNumber)
        i.putExtra("fullPath",fullPath)
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);

    }
    private fun publishNotification(percent: Int,url:String) {
        val i = Intent("message_progress")
        i.putExtra("percent_download", percent)
        i.putExtra("url", url)
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }
    override fun onBind(intent: Intent?): IBinder? {
            return binder;
    }
    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
    fun requestBody(fileUrl: String?): Request? {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("azure_id", azureid)
            .addFormDataPart(
                "downloadUrl", fileUrl!!
            )
            .build()
        return Request.Builder()
            .url("https://development.mind-zone.net/projects/robroo/index.php/webservice/api/v1/getSKABoxDownload")
            .post(requestBody)
            .header("Content-Type", " application/x-www-form-urlencoded")
            .build()
    }
    @SuppressLint("StaticFieldLeak")
    inner class DownloadFileFromURL(var item: Items) :
        AsyncTask<String?, String?, String?>() {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        var path = item.docid.toString()+item.docnumber + item.productmodelcode
        var fullpath= "/" +item.docnumber + ".pdf"
        override fun doInBackground(vararg params: String?): String? {
                var percent:Int
                val response = client.newCall(requestBody(params[0])!!)
                response.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("isBind",item.toString())
                    }
                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            listLoading.removeIf { it== path }
                        }
                        response.body()?.let {
                            try {
                                val fileSize = it.contentLength()
                                var fileSizeDownloaded: Long = 0
                                inputStream = it.byteStream()
                                val imageFile = File(baseContext.externalCacheDir!!.absolutePath, fullpath)
                                outputStream = FileOutputStream(imageFile)
                                val fileReader = ByteArray(8192)
                                while (true) {
                                    val read = inputStream!!.read(fileReader)
                                    if (read == -1) {
                                        break;
                                    }
                                    outputStream?.write(fileReader, 0, read)
                                    fileSizeDownloaded += read.toLong()
                                    percent = (fileSizeDownloaded * 100 / fileSize).toInt()
                                    publishProgress(percent.toString(),path)

                                }
                                outputStream?.flush()
                            } catch (e: Exception) {
                                Log.e("Error: ", e.message.toString())
                            } finally {
                                if (inputStream != null) {
                                    inputStream!!.close()
                                }
                                outputStream!!.close()
                                publishSuccess(item.docnumber,fullpath)
                            }
                        }
                    }

                })
            return null
        }
        override fun onProgressUpdate(vararg values: String?) {
            publishNotification(values[0]!!.toInt(),values[1]!!)
        }
        override fun onPostExecute(a:String?) {
        }


    }

}