package net.mindzone.robroo.ui.main.notification.knowledge

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notificaton_knowledge.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.notification.entity.Notification
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.notification.NotificationGeneralAndKnowledgeViewModel
import net.mindzone.robroo.ui.main.notification.general.NotificationKnowledgeAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager
import javax.inject.Inject

@AndroidEntryPoint
class NotificationKnowledgeFragment @Inject constructor(): BaseFragment(R.layout.fragment_notificaton_knowledge) {
    private lateinit var notificationKnowledgeAdapter: NotificationKnowledgeAdapter
    private val viewModel by viewModels<NotificationGeneralAndKnowledgeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"NotificationKnowledgeFragment",0,"")
        rvListknowledge!!.apply {
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(5)

        }
        viewModel.getNotifications("2", MainActivity.user!!.azureoid,0) //fake azure_id("0000-0000-0000-0000")
//        viewModel.getNotifications("2", "0000-0000-0000-0000",0) //fake azure_id("0000-0000-0000-0000")

    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {

            status.observe(viewLifecycleOwner){
                Log.d("status",status.value.toString())
                when(it){
                    Status.LOADING->rvListknowledge.veil()

                    Status.SUCCESS->rvListknowledge.unVeil()

                }
            }
            notificationResponse.observe(viewLifecycleOwner) {
                setUpAdapter(it)
                Log.d("respone", it.size.toString())
            }


        }
    }

    private fun setUpAdapter(list: List<Notification>) {
        notificationKnowledgeAdapter = requireActivity()?.let { NotificationKnowledgeAdapter(it, list) }!!

        if (list.isEmpty()) {
            rvListknowledge.visibility = View.GONE;
            layoutNoData.visibility = View.VISIBLE
        }
        else {
            layoutNoData.visibility = View.GONE;
            rvListknowledge.visibility = View.VISIBLE;
            rvListknowledge.setAdapter(notificationKnowledgeAdapter)

        }
    }
}