package net.mindzone.robroo.ui.main.share.listShare.viewPager.listAll

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_all.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticles
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumGroups
import net.mindzone.robroo.databinding.FragmentListAllBinding
import net.mindzone.robroo.ui.main.share.listShare.ListAllAdapter
import net.mindzone.robroo.ui.main.share.listShare.ShareFragment
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel
import net.mindzone.robroo.ui.main.share.listShare.listDetail.ListDetailFragment
import net.mindzone.robroo.ui.main.share.listShare.viewPager.FilterListAllBottomSheet
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.OnLoadMoreListener
import net.mindzone.robroo.utils.RecyclerViewLoadMoreScroll
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class ListAllFragment : BaseFragment(R.layout.fragment_list_all) {
    private lateinit var listAllShareAdapter: ListAllAdapter
    private val viewModel by viewModels<ShareViewModel>()
    private lateinit var forumGroup: ForumGroups
    private lateinit var azureId: String
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var listForumArticles: MutableList<ForumArticles?>? = null
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var runnable: Runnable
    var search = ""
    var count = 0
    var sortCommentOrDate = "Date"

    companion object {
        const val EXTRA_FORUM_ARTICLES = "ForumArticles"
        const val EXTRA_TAG = "tag"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(
            RobRooApplication.applicationContext(),
            AuditType.screen,
            "ListAllFragment",
            0,
            ""
        )
        forumGroup = arguments?.getSerializable(ShareFragment.EXTRA_FORUM_GROUP) as ForumGroups
        azureId = viewModel.sharedPreferencesHelper.getCurrentUserId()
        viewModel.getForumArticles(forumGroup.groupId, search, 0, azureId, "", 0)
        initRecycleView()
        setFragmentResultListener()
        searchTag()
        setRVLayoutManager()
        setRVScrollListener()
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
        (viewDataBinding as FragmentListAllBinding).apply {
            fragmentViewModel = viewModel
        }
    }

    override fun startObservingValues() {
        viewModel.apply {
            forumArticlesResponse.observe(viewLifecycleOwner) {
                loading.visibility = View.GONE
                if (it.isSuccessful) {
                    listAllShareAdapter.removeLoadingView()
                    scrollListener.setLoaded()
                    if (it.body()!!.responseData.forumArticles.isNotEmpty()) {
                        listForumArticles = it.body()!!.responseData.forumArticles.toMutableList()
                        checkListNullOrEmpty(listForumArticles!!)
                        if (!listForumArticles.isNullOrEmpty()) {
                            listAllShareAdapter.addList(listForumArticles!!)
                        }

                    }
                }
            }
            handleFilterList.observe(viewLifecycleOwner) {
                handleFilterBottomSheet()
            }

        }

    }


    private fun handleFilterBottomSheet() {
        val bottomSheet =
            FilterListAllBottomSheet(object : FilterListAllBottomSheet.BottomSheetListener {
                // item bottom sheet click
                override fun onOptionClick(text: String) {
                    sortCommentOrDate = text
                    count = 0
                    listAllShareAdapter.clearList()
                    viewModel.getForumArticles(forumGroup.groupId, "", 0, azureId, sortCommentOrDate, 0)

                }
            })
        val bundle = Bundle()
        bundle.putString("forumGroup", forumGroup.groupId)
        bottomSheet.arguments = bundle
        bottomSheet.show(
            requireActivity().supportFragmentManager.beginTransaction(), "FilterListAllBottomSheet"
        )
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(context)
        rvListAll.layoutManager = mLayoutManager
        rvListAll.setHasFixedSize(true)
    }

    private fun initRecycleView() {

        listAllShareAdapter = ListAllAdapter(requireActivity(), viewModel, this) {
            navigateListDetailFragment(it)
        }
        listAllShareAdapter.notifyDataSetChanged()
        rvListAll.adapter = listAllShareAdapter

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
            EXTRA_TAG,
            this
        ) { _, bundle ->
            val filter: String? = bundle.getString("tag")
            etSearch.setText(filter)
            etSearch.setSelection(etSearch.text.toString().length)
            viewModel.getForumArticles(forumGroup.groupId, "", 0, azureId, sortCommentOrDate, 0)
        }
    }

    fun searchTag() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                search = s.toString()
                count = 0
                listAllShareAdapter.clearList()
                queryEvent()
            }

        })
    }

    private fun setRVScrollListener() {
        mLayoutManager = LinearLayoutManager(context)
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
        rvListAll.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData() {
        runnable = Runnable { queryEvent() }
        listAllShareAdapter.addLoadingView()
        count += 1
        val countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                queryEvent()
            }
        }
        countDownTimer.start()
    }

    fun queryEvent() {
        viewModel.getForumArticles(forumGroup.groupId, search, count, azureId, sortCommentOrDate, 0)

    }

    private fun checkListNullOrEmpty(list: MutableList<ForumArticles?>) {
        if (list.isNullOrEmpty()) {
            rvListAll.visibility = View.GONE
            ic_noDataMenu4.visibility = View.VISIBLE
        } else {
            ic_noDataMenu4.visibility = View.GONE
            rvListAll.visibility = View.VISIBLE
        }
    }
}