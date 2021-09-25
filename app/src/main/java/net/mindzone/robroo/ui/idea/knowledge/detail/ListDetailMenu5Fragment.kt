package net.mindzone.robroo.ui.main.idea.knowledge.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.afollestad.viewpagerdots.DotsIndicator
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_detail_menu5.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.Items
import net.mindzone.robroo.data.news.entity.newsfeedContent.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.idea.IdeaViewModel
import net.mindzone.robroo.ui.viewpager.ImageAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class ListDetailMenu5Fragment : BaseFragment(R.layout.fragment_list_detail_menu5) {
    private val viewModel by viewModels<IdeaViewModel>()
    private var runnable: Runnable? = null
    private lateinit var handler: Handler
    var lastPosition = 0
    var currentPosition = 0
    var azure_id= MainActivity.user?.azureoid!!
    private lateinit var adapter: ImageAdapter
    private lateinit var dotsIndicator: DotsIndicator
    private lateinit var expTextView: TextView
    private lateinit var buttonNext: Button
    var title = ""
    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        val items: Items = bundle?.getSerializable("items") as Items

        title=bundle.getString("title8")!!
        txtTile.text=title

        viewModel.getNewsFeedContent(azure_id,items.contentid)

        buttonNext= view.findViewById(R.id.btnNextMenu5)
        expTextView= view.findViewById(R.id.tvContent)

        buttonNext.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,items.contentid,items.titleth)
                openBottomSheet(items.linkth)
        }
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
    }
    override fun startObservingValues() {
        viewModel.apply {
            newsFeedContentResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null){
                        setAdapter((it.body()?.responseData!!))

                    }
//                    else{

//                    }
                }
            }
            status.observe(viewLifecycleOwner){
                when(it){
                    Status.LOADING -> veilLayout.veil()
                    Status.SUCCESS -> veilLayout.unVeil()
                    Status.ERROR -> {
                        veilLayout.visibility = View.GONE
                        layoutNoDataI7.visibility = View.VISIBLE
                    }
                }

            }
         }
    }
    private fun setAdapter(re: ResponseData) {
        txtTitle.text = re.titleth
        tvDateTimeDetail.text = re.contentdatetime
        txtTitleM.text = re.viewstats.toString()
        tvContent.text = re.fullcontentth
        Glide.with(this).load(re.imageth).into(imgMenu5)
    }

    private fun openBottomSheet(title :String) {
        val webpagerDialogFragment = ActionBottom.newIntance()
        val bundle = Bundle()
        bundle.putString("title9",title)
        webpagerDialogFragment.arguments = bundle
        webpagerDialogFragment.show(
            requireActivity().supportFragmentManager.beginTransaction(), "BottomSheet"
        )
    }


}