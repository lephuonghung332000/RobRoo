package net.mindzone.robroo.ui.main.share.listShare.viewPager

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_my_blog_list.*
import net.mindzone.robroo.R
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticles
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumGroups
import net.mindzone.robroo.databinding.FragmentMyBlogListBinding
import net.mindzone.robroo.ui.main.share.listShare.ListShareAdapter
import net.mindzone.robroo.ui.main.share.listShare.ShareFragment.Companion.EXTRA_FORUM_GROUP
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel
import net.mindzone.robroo.ui.main.share.listShare.listDetail.ListDetailFragment
import net.mindzone.robroo.ui.main.share.listShare.viewPager.listAll.ListAllFragment.Companion.EXTRA_FORUM_ARTICLES
import net.mindzone.robroo.ui.main.share.listShare.writeMyBlog.WriteMyBlogFragment
import net.mindzone.robroo.utils.OnLoadMoreListener
import net.mindzone.robroo.utils.RecyclerViewLoadMoreScroll

@AndroidEntryPoint
class MyBlogListFragment : BaseFragment(R.layout.fragment_my_blog_list), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel by viewModels<ShareViewModel>()

    private lateinit var listPostedAdapter: ListShareAdapter
    private lateinit var forumGroup: ForumGroups
    lateinit var runnable:Runnable
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var azureId: String
    private var idForumArticleSaveDraft = 0
    var count=0
    private lateinit var deleteArticle: String
    private var listForumArticles: MutableList<ForumArticles?>? = null
    private var listCheckNull = mutableListOf<ForumArticles>()

    companion object {
        /** if true will insert forum article into local database else update */
        const val CHECK_UPDATE_OR_INSERT = "check_update_or_insert"
        const val EXTRA_FORUM_ARTICLE_SAVE_DRAFT = "forum_article_save_draft"
        const val EXTRA_FORUM_GROUP_ID = "forum_group_id"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("adfasdfasfasdfasdf", "onAttach")
    }

    override fun onStop() {
        super.onStop()
        Log.d("adfasdfasfasdfasdf", "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d("adfasdfasfasdfasdf", "onPause")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("adfasdfasfasdfasdf", "onViewCreated")
        initRecycleView()
        setFragmentResultListener()
        setRVLayoutManager()
        setRVScrollListener()
        setEvent()
    }

    private fun setEvent() {
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onStart() {
        super.onStart()
        Log.d("adfasdfasfasdfasdf", "onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("adfasdfasfasdfasdf", "onDestroy")
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
        (viewDataBinding as FragmentMyBlogListBinding).apply {
            fragmentViewModel = viewModel
        }
    }

    private fun checkListNullOrEmpty(list: MutableList<ForumArticles>?) {

        if (list.isNullOrEmpty()) {
            imageViewPost.visibility = View.VISIBLE
            tvNoPost.visibility = View.VISIBLE
            tvPosted.visibility = View.GONE
            progressbarloading.visibility = View.GONE
        } else {
            imageViewPost.visibility = View.GONE
            tvNoPost.visibility = View.GONE
            tvPosted.visibility = View.VISIBLE
        }
    }

    override fun startObservingValues() {
        getDataFromShareFragment()
        viewModel.apply {
            getForumArticles(forumGroup.groupId, "", 0, azureId, "date", 1)
            navigateToWriteMyBlog.observe(viewLifecycleOwner) {
                navigateToWriteMyBlogListFragment(ForumArticleSaveDraft(), true)
            }
            forumArticlesResponse.observe(viewLifecycleOwner) {
                progressbarloading.visibility = View.GONE

                if (it.isSuccessful) {
                    if(swipeRefreshLayout.isRefreshing){
                        swipeRefreshLayout.isRefreshing = false
                        listPostedAdapter.clearList()
                    }
                    listPostedAdapter.removeLoadingView()
                    scrollListener.setLoaded()
                    listCheckNull?.addAll(it.body()!!.responseData.forumArticles.toMutableList())
                    checkListNullOrEmpty(listCheckNull)
                    listForumArticles = it.body()!!.responseData.forumArticles.toMutableList()

                    if (!listForumArticles.isNullOrEmpty()){
                    listPostedAdapter.submitList(listForumArticles!!)
                    }

                }

            }

        }
    }

    private fun getDataFromShareFragment() {
        forumGroup = arguments?.getSerializable(EXTRA_FORUM_GROUP) as ForumGroups
        azureId = viewModel.sharedPreferencesHelper.getCurrentUserId()
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(context)
        rvListPosted.layoutManager = mLayoutManager
        rvListPosted.setHasFixedSize(true)
    }

    private fun initRecycleView() {

        listPostedAdapter = ListShareAdapter(requireActivity(),viewModel, this) {
            navigateListDetailFragment(it)
        }
        listPostedAdapter.notifyDataSetChanged()
        rvListPosted.adapter = listPostedAdapter



    }
    private fun setRVScrollListener() {
        mLayoutManager = LinearLayoutManager(context)
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
        rvListPosted.addOnScrollListener(scrollListener)
    }
    private fun loadMoreData() {
        runnable= Runnable { queryEvent() }
        listPostedAdapter.addLoadingView()
        count++
        val countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                queryEvent()
            } }
        countDownTimer.start()
    }
    fun queryEvent(){
        viewModel.getForumArticles(forumGroup.groupId, "", count, azureId, "date", 1)

    }
    private fun navigateToWriteMyBlogListFragment(
        forumArticleSaveDraft: ForumArticleSaveDraft,
        typeSaveDraft: Boolean
    ) {
        val bundle = Bundle()
        bundle.putSerializable(EXTRA_FORUM_ARTICLE_SAVE_DRAFT, forumArticleSaveDraft)
        bundle.putString(EXTRA_FORUM_GROUP_ID, forumGroup.groupId)
        bundle.putBoolean(CHECK_UPDATE_OR_INSERT, typeSaveDraft)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val fmWriteMyBlog = WriteMyBlogFragment()
        fmWriteMyBlog.arguments = bundle
        transaction.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_to_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_to_right
        )
            .add(R.id.frameMain, fmWriteMyBlog)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateListDetailFragment(forumArticles: ForumArticles) {
        val bundle = Bundle()
        bundle.putSerializable(EXTRA_FORUM_ARTICLES, forumArticles)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val fmListDetail = ListDetailFragment()
        fmListDetail.arguments = bundle
        transaction.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_to_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_to_right
        )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .add(R.id.frameMain, fmListDetail)
            .addToBackStack(null)
            .commit()
    }

    private fun setFragmentResultListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "update forum articles",
            this
        ) { _, _ -> viewModel.getForumArticles(forumGroup.groupId, "", 0, azureId, "date", 1) }
    }

    override fun onRefresh() {
        viewModel.getForumArticles(forumGroup.groupId, "", 0, azureId, "date", 1)

    }

}