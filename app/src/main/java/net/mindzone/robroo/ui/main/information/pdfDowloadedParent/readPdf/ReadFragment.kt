package net.mindzone.robroo.ui.main.information.pdfDowloadedParent.readPdf

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_toolbar_03.*
import net.mindzone.robroo.R
import net.mindzone.robroo.base.BaseFragment
import java.io.File

@AndroidEntryPoint
class ReadFragment : BaseFragment(R.layout.fragment_read) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pdf : PDFView
        pdf=view.findViewById(R.id.pdfView)
        val fileCheckExsistName = arguments?.getString("linkPdf")
        val file = File(fileCheckExsistName)
        pdf.fromFile(file)
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            // allows to draw something on the current page, usually visible in the middle of the screen
            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(0)
            .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
            .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
            .fitEachPage(true) // fit each page to the view, else smaller pages are scaled relative to largest page.
            .pageSnap(false) // snap pages to screen boundaries
            .pageFling(false) // make a fling change only a single page like ViewPager
            .nightMode(false) // toggle night mode
            .load();
        btnDone.setOnClickListener{
           activity?.supportFragmentManager?.popBackStack()

        }
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
    }

    override fun startObservingValues() {
    }


}