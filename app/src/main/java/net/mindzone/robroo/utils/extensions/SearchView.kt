package net.mindzone.robroo.utils.extensions


import android.animation.Animator
import android.content.Context
import android.os.Build
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_search.view.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


class SearchView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    var imm = (context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)!!
    init {

        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)
        search_closed_view.setOnClickListener { openSearch() }
        close_search_button.setOnClickListener { closeSearch() }

    }
    fun addTextChangeListener(textWatcher: TextWatcher) {
        search_input_text.addTextChangedListener(textWatcher)
    }
    private fun openSearch() {
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,"openSearch")
        search_input_text.requestFocus()
        imm!!.showSoftInput(search_input_text, InputMethodManager.SHOW_IMPLICIT)
        search_open_view.visibility = View.VISIBLE
        val circularReveal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimationUtils.createCircularReveal(
                search_open_view,
                (open_search_button.right + open_search_button.left) / 2,
                (open_search_button.top + open_search_button.bottom) / 2,
                0f, width.toFloat()
            )
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }
        circularReveal.duration = 300
        circularReveal.start()

    }

    private fun closeSearch() {
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,"closeSearch")
        imm!!.hideSoftInputFromWindow(search_input_text.getWindowToken(), 0)
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            search_open_view,
            (open_search_button.right + open_search_button.left) / 2,
            (open_search_button.top + open_search_button.bottom) / 2,
            width.toFloat(), 0f
        )

        circularConceal.duration = 300
        circularConceal.start()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                search_open_view.visibility = View.INVISIBLE
                search_input_text.setText("")
                circularConceal.removeAllListeners()
            }
        })
    }


}