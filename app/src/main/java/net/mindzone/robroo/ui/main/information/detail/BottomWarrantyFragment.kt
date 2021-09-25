package net.mindzone.robroo.ui.main.information.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_web_dialog.imgCancel
import kotlinx.android.synthetic.main.fragment_bottom_warranty.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


@AndroidEntryPoint
class BottomWarrantyFragment : BottomSheetDialogFragment() {
    private var fragmentView: View? = null
    private val viewModel by viewModels<InformationViewModel>()
    private lateinit var webView: WebView
    var PEEK_HEIGHT : Int = 980
    var title = ""
    var azure_id= MainActivity.user?.azureoid!!
    var warranty=""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_bottom_warranty, container, false)
        return fragmentView
    }
    fun startObservingValues() {
        viewModel.apply {
            warrantyResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        warranty=it.body()?.responseData!!.warranty_detail
                        Log.d("musssssa",warranty)
                        webView.loadDataWithBaseURL(null, warranty, "text/html", "utf-8", null);
                    }
                    else {
                    }
                }
            }
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservingValues()
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"BottomWarrantyFragment",0,"")
        webView= view?.findViewById(R.id.webView)!!
        webView.getSettings().setJavaScriptEnabled(true);
        setWebView()
        if(arguments?.getString("title9")!=null){
            title=arguments?.getString("title9")!!
            titlebWeb.text=title
        }
        if(arguments?.getString("detail")!=null){
            title=arguments?.getString("detail")!!
            webView.loadDataWithBaseURL(null, title, "text/html", "utf-8", null);
        }
        if(arguments?.getString("title_share")!=null){
            title=arguments?.getString("title_share")!!
            viewModel.getWarrantyInfo(azure_id,title)
            titlebWeb.text=title
        }
        if(arguments?.getString("linkService")!=null){
            title=arguments?.getString("linkService")!!
            webView.getSettings().setDomStorageEnabled(true)
            webView.loadUrl(title);
            titlebWeb.text=""
        }
        setEvent()

        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheetBehavior = bottomSheetDialog.behavior
        bottomSheetBehavior.isDraggable = false

        val offsetFromTop = 150
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = false
            setExpandedOffset(offsetFromTop)
            state = BottomSheetBehavior.STATE_EXPANDED
        }

    }

    private fun setWebView() {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                return if (url.startsWith("http:") || url.startsWith("https:")) {
                    false
                } else {
                    if (url.startsWith("intent://")) {
                        try {
                            val context: Context = webView.context
                            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            if (intent != null) {
                                val packageManager: PackageManager = context.getPackageManager()
                                val info = packageManager.resolveActivity(
                                    intent,
                                    PackageManager.MATCH_DEFAULT_ONLY
                                )
                                // This IF statement can be omitted if you are not strict about
                                // opening the Google form url in WebView & can be opened in an
                                // External Browser
                                if (intent != null && (intent.scheme == "https"
                                            || intent.scheme == "http")
                                ) {
                                    val fallbackUrl = intent.getStringExtra(
                                        "browser_fallback_url"
                                    )
                                    webView.loadUrl(fallbackUrl!!)
                                    return true
                                }
                                if (info != null) {
                                    context.startActivity(intent)
                                } else {
                                    // Call external broswer
                                    val fallbackUrl = intent.getStringExtra(
                                        "browser_fallback_url"
                                    )
                                    val browserIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(fallbackUrl)
                                    )
                                    context.startActivity(browserIntent)
                                }
                                true
                            } else {
                                false
                            }
                        } catch (e: Exception) {
                            false
                        }
                    } else {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        webView.context.startActivity(intent)
                        true
                    }
                }
            }
        }
    }

    private fun setEvent() {
        imgCancel.setOnClickListener {
            dismiss()
        }
    }
}