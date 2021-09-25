package net.mindzone.robroo.ui.main.idea.knowledge
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_unread_knowledge.*
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
class UnreadKnowledgeFragment : BaseFragment(R.layout.fragment_unread_knowledge) {
    private val viewModel by viewModels<IdeaViewModel>()
    private lateinit var unreadKnowledgeAdapter: UnreadKnowledgeAdapter
    val azureId = MainActivity.user!!.azureoid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"UnreadKnowledgeFragment",0,"")
        var id =arguments?.getInt("id_6")!!

        rvMenu5ListUnreadKnowledge!!.apply {
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(7)
        }
        viewModel.getListByGroup(azureId,id)


    }
    companion object {
        fun newInstance(id: Int): Fragment {
            val f = UnreadKnowledgeFragment()
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
                        rvMenu5ListUnreadKnowledge.visibility = View.GONE
                        layoutNoDataI7.visibility = View.VISIBLE
                    }
                } else {

                }
            }
            status.observe(viewLifecycleOwner){
                when(it){
                    Status.LOADING -> rvMenu5ListUnreadKnowledge.veil()
                    Status.SUCCESS -> rvMenu5ListUnreadKnowledge.unVeil()
                }

            }
        }
    }

    private fun setAdapter(list: ResponseData) {
        unreadKnowledgeAdapter = UnreadKnowledgeAdapter(list,requireActivity())
        rvMenu5ListUnreadKnowledge.apply {
            setAdapter(unreadKnowledgeAdapter)
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(7)
        }

    }

}