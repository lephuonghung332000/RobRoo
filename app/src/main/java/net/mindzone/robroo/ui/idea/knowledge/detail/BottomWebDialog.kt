package net.mindzone.robroo.ui.main.idea.knowledge.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_web_dialog.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


class BottomWebDialog : BottomSheetDialogFragment() {
    private var fragmentView: View? = null
    private lateinit var webView: WebView
    var PEEK_HEIGHT : Int = 980
    var title = ""


    override fun getTheme(): Int {
        return R.style.BottomSheetMenuTheme
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetMenuTheme)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.bottom_web_dialog, container, false)
        return fragmentView
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView= view.findViewById(R.id.webView)!!
        webView.getSettings().setJavaScriptEnabled(true);
        webView.webViewClient = WebViewClient()
        try {
            val bundle = arguments
            title = bundle?.getString("title9")!!
            webView.loadUrl(title)
        }catch (e : NullPointerException){

        }finally {
            if(title==""){
                title = resources.getString(R.string.uri)
                titleWed.text = title
            }else {
                titleWed.text = title
            }
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

    private fun setEvent() {
       imgCancel.setOnClickListener {
           dismiss()
           AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,title)
       }
    }
}