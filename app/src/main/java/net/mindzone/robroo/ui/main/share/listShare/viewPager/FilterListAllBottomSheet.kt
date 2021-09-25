package net.mindzone.robroo.ui.main.share.listShare.viewPager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_filter_bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.fragment_filter_bottom_sheet_dialog.view.*
import net.mindzone.robroo.R
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticles
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumGroups
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.pdfDowloadedParent.PdfBottomSheetDialogManualFragment
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel
@AndroidEntryPoint
class FilterListAllBottomSheet(var mBottomSheetListener: BottomSheetListener?) : BottomSheetDialogFragment() {
    private val viewModel by viewModels<ShareViewModel>()
    private lateinit var forumGroup: String
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        forumGroup = arguments?.getString("forumGroup").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_filter_bottom_sheet_dialog, container, false)
//        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        setEvent(v)
        return v
    }

    override fun onDetach() {
        super.onDetach()
        mBottomSheetListener = null
    }

    fun setEvent(v: View){
        v.tvPopularOpinion.setOnClickListener {
            mBottomSheetListener!!.onOptionClick("comments")
            dismiss() //dismiss bottom sheet when item click
        }
        v.tvLatestNews.setOnClickListener {
            mBottomSheetListener!!.onOptionClick("Date")
            dismiss() //dismiss bottom sheet when item click

        }
        v.linearLayoutCancel.setOnClickListener {
            mBottomSheetListener?.onOptionClick("")
            dismiss()
        }
    }

    interface BottomSheetListener {
        fun onOptionClick(text: String)
    }


}