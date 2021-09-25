package net.mindzone.robroo.ui.main.idea.knowledge

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_flash_knowledges.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.news.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.idea.IdeaViewModel
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class AllFlashKnowledgesFragment : BaseFragment(R.layout.fragment_all_flash_knowledges) {
    private val viewModel by viewModels<IdeaViewModel>()
    val azureId = MainActivity.user!!.azureoid
    private lateinit var ideaKnowledgeAdapter: IdeaKnowledgeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"AllFlashKnowledgesFragment",0,"")
        val id =arguments?.getInt("id_6")!!
        viewModel.getListByGroup(azureId,id)
    }
    companion object {
        fun newInstance(id: Int): Fragment {
            val f = AllFlashKnowledgesFragment()
            val b = Bundle()
            b.putInt("id_6", id)
            f.arguments = b
            return f
        }
    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {
            listByGroupResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null){
                        setAdapter((it.body()?.responseData!!))
                    }
                    else{
                        rvMenu5ListKnowledge.visibility = View.GONE
                        layoutNoDataI6.visibility = View.VISIBLE
                    }
                } else {

                }
            }
            status.observe(viewLifecycleOwner){
                when(it){
                    Status.LOADING -> rvMenu5ListKnowledge.veil()
                    Status.SUCCESS -> rvMenu5ListKnowledge.unVeil()
                }

            }
        }
    }

    private fun setAdapter(list: ResponseData) {
            ideaKnowledgeAdapter = IdeaKnowledgeAdapter(list,requireActivity())
            rvMenu5ListKnowledge.apply {
                setAdapter(ideaKnowledgeAdapter)
                setLayoutManager(LinearLayoutManager(context))
                setVeilLayout(R.layout.cell_skeleton)
                addVeiledItems(7)
            }

     }
}


