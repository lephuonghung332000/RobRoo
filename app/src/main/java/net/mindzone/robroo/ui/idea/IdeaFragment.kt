package net.mindzone.robroo.ui.main.idea

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_idea.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.idea.knowledge.AllAndUnreadKnowledgeFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class IdeaFragment : BaseFragment(R.layout.fragment_idea) {
    private val viewModel by viewModels<IdeaViewModel>()
    private lateinit var ideaAdapter: IdeaAdapter
    var flag=false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"knowledge_menu",0,"")
        viewModel.getListNewsTopMost(MainActivity.user!!.azureoid)
        rvMenu5Home!!.apply {
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(7)
        }

    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {
            listNewsTopMostResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null){
                        setAdapter(it.body()?.responseData!!)
                        it.body()?.responseData!!.forEach {
                        }
                       }
                    else{
                        rvMenu5Home.visibility = View.INVISIBLE
                        layoutNoDataI5.visibility = View.VISIBLE
                    }
                } else {

                }

            }
            status.observe(viewLifecycleOwner){
                when(it){
                    Status.LOADING->{
                        rvMenu5Home.veil()

                    }
                    Status.SUCCESS->{
                        rvMenu5Home.unVeil()
                        flag=true
                    }
                }
                if(flag){ rvMenu5Home.unVeil()}

            }
        }

    }

    private fun setAdapter(list: List<ResponseData>) {
        context?.let {
            ideaAdapter = IdeaAdapter(list, it ,itemListener= { s: String, i: Int ->
                val createFragment = AllAndUnreadKnowledgeFragment()
                val bundle = Bundle()
                bundle.putString("tittle5", s)
                bundle.putInt("id5", i)
                createFragment.arguments = bundle
                fragmentManager?.beginTransaction() ?.setCustomAnimations(
                        R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right
                )
                        ?.replace(R.id.frameMain, createFragment)?.addToBackStack(null)?.commit()
            })
            rvMenu5Home.setAdapter(ideaAdapter)
        }
    }
}