package net.mindzone.robroo.ui.main.share.listShare.listDetail

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import co.lujun.androidtagview.TagView
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_detail.*
import kotlinx.android.synthetic.main.fragment_list_detail.veilLayout
import kotlinx.android.synthetic.main.fragment_list_detail_menu5.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.view.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticle
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticles
import net.mindzone.robroo.databinding.FragmentListDetailBinding
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel
import net.mindzone.robroo.ui.main.share.listShare.listDetail.viewPager.ViewPagerAdapter
import net.mindzone.robroo.ui.main.share.listShare.viewPager.listAll.ListAllFragment.Companion.EXTRA_FORUM_ARTICLES
import net.mindzone.robroo.ui.main.share.listShare.viewPager.listAll.ListAllFragment.Companion.EXTRA_TAG
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager
import java.util.*

@AndroidEntryPoint
class ListDetailFragment : BaseFragment(R.layout.fragment_list_detail) {
    private val viewModel by viewModels<ShareViewModel>()
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var forumArticles: ForumArticles
    private var forumArticle = ForumArticle()
    private var timer = Timer()
    private var currentPage = 0
    private lateinit var  articleId : String



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ListDetailFragment",0,"")
        includeCustomToolbar02.txtTile.text = "แบ่งปัน"
        initRecycleView()
        setClick()
        getCommentInSharePref(articleId)
    }



    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
        (viewDataBinding as FragmentListDetailBinding).apply {
            fragmentViewModel = viewModel
        }
        forumArticles = arguments?.getSerializable(EXTRA_FORUM_ARTICLES) as ForumArticles
         articleId = forumArticles.articleId
        val azureId = viewModel.sharedPreferencesHelper.getCurrentUserId()
        viewModel.getForumArticleComments(articleId, azureId)

        viewModel.apply {
            getForumArticle(articleId, azureId)
            forumArticleResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {

                    if (it.body()?.responseData?.forumArticle != null) {
                        forumArticle = it.body()!!.responseData.forumArticle
                        viewDataBinding.forumArticle = forumArticle
                        tvLikeForumArticle.value = forumArticle.likes.toString()
                        toggleIconLike.value = forumArticle.isLiked == 1
                        isLiked.value = forumArticle.isLiked.toString()
                        isLikedToggle.value = forumArticle.isLiked.toString()
                        initTagView(forumArticle.tags)
                        initPagerAdapter(forumArticle.images)
                        setFragmentResult("")
                    }
                }
            }
        }
    }

    override fun startObservingValues() {
        viewModel.commentsResponse.observe(viewLifecycleOwner) {
            etComment.text?.clear()
            if (it.isSuccessful) {
                loading.visibility = View.GONE

                if (it.body()?.responseData?.comments?.isNotEmpty()!!) {
                    commentAdapter.submitList(it.body()?.responseData?.comments!!.toMutableList())
                }
            }
        }
        viewModel.isButtonCommentClick.observe(viewLifecycleOwner) {
            if (rvComments.height != 88) setSmoothScrollLayout("ViewComment")
        }
        viewModel.status.observe(viewLifecycleOwner){
            when(it){
                Status.LOADING -> veilLayout.veil()
                Status.SUCCESS -> veilLayout.unVeil()
                Status.ERROR -> {
                    veilLayout.visibility = View.GONE
                    layoutNoData.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun setFragmentResult(tag: String) {
        requireActivity().supportFragmentManager.setFragmentResult(
            EXTRA_TAG, bundleOf("tag" to tag)
        )
        if (tag != "") requireActivity().supportFragmentManager.popBackStack()
    }

    private fun initTagView(tags: List<String>) {
        val filterTags = tags.filter { it != "" }
        listTagview.tags = filterTags
        listTagview.setOnTagClickListener(object : TagView.OnTagClickListener {

            override fun onTagClick(position: Int, text: String) {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,"listTagview")

                listTagview.toggleSelectTagView(position)
                listTagview.getTagView(position).tagSelectedBackgroundColor =
                    Color.parseColor("#9FD2D2")
                setFragmentResult(text)
            }

            override fun onTagLongClick(position: Int, text: String) {}
            override fun onSelectedTagDrag(position: Int, text: String) {}
            override fun onTagCrossClick(position: Int) {}
        })
    }

    private fun initPagerAdapter(images: List<String>) {
        val viewPagerAdapter = ViewPagerAdapter(context, images)
        viewPagerSlide.adapter = viewPagerAdapter
        dotsIndicator.attachViewPager(viewPagerSlide)
        timer.cancel()
        //slide animation
        viewPagerSlide.setPageTransformer(
            false
        ) { page, _ ->
            page.alpha = 0f
            page.visibility = View.VISIBLE
            // Start Animation for a short period of time
            page.animate().alpha(1f).duration =
                page.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        }

        //slide auto
        val handler = Handler()
        val update = Runnable {
            if (currentPage == images.size) {
                currentPage = 0
            }
            viewPagerSlide.setCurrentItem(currentPage++, true)
        }
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 500, 4000)

    }

    private fun initRecycleView() {
        commentAdapter = CommentAdapter(viewModel)
        rvComments.apply {
//            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            adapter = commentAdapter
        }
        commentAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val type = if (positionStart == 0) "ViewComment" else "InsertItemRecycleView"
                setSmoothScrollLayout(type)
            }
        })
    }

    private fun setSmoothScrollLayout(typeScroll: String) {
        rvComments.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    nestedScrollView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val positionScroll =
                        if (typeScroll == "ViewComment" && viewModel.isScrollTopOrBottomListComment.value == true) rvComments.top else rvComments.bottom
                    nestedScrollView.fling(positionScroll)
                    nestedScrollView.smoothScrollTo(0, 88)
                }
            })
    }

    override fun onPause() {
        super.onPause()
        if (!viewModel.textComment.value.equals("")){
        saveCommentToSharePref()}
    }
    fun saveCommentToSharePref(){
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences( "comment", Context.MODE_PRIVATE
        )
        sharedPref.edit().putString(articleId,etComment.text.toString()).apply()

        Log.d("testttttt", "saved articleId" + etComment.text)

    }
    private fun getCommentInSharePref(key: String){
        var txt = requireContext().getSharedPreferences("comment", Context.MODE_PRIVATE).getString(key, "") ?:""
        viewModel.textComment.value = txt
        Log.d("testttttt", "show articleId" + txt)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }
    fun setClick(){
        etSent.setOnClickListener{
            viewModel.clickComment(forumArticle)
            showLoading()
            requireContext().getSharedPreferences("comment", Context.MODE_PRIVATE)!!.edit().remove(forumArticle.articleId).apply()
            viewModel.doneComment.observe(viewLifecycleOwner){
                if (it)
                hideLoading()
            }
        }
    }
}
