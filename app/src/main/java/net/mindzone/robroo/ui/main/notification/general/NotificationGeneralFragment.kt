package net.mindzone.robroo.ui.main.notification.general

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notification_general.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.notification.entity.Notification
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.notification.NotificationGeneralAndKnowledgeViewModel
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class NotificationGeneralFragment : BaseFragment(R.layout.fragment_notification_general) {
    private lateinit var notificationGeneralAdapter: NotificationGeneralAdapter
    private val viewModel by viewModels<NotificationGeneralAndKnowledgeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"NotificationGeneralFragment",0,"")
        viewModel.getNotifications("1", MainActivity.user!!.azureoid,0)
        
//        viewModel.getNotifications("1", "0000-0000-0000-0000",0)
        rvListAll!!.apply {
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(5)

        }
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {
            status.observe(viewLifecycleOwner){
                Log.d("status",status.value.toString())
                when(it){
                    Status.LOADING->rvListAll.veil()
                    Status.SUCCESS-> rvListAll.unVeil()
                }
            }

            notificationResponse.observe(viewLifecycleOwner) {
                setUpAdapter(it)
                Log.d("respone", it.size.toString())
            }


        }
    }

    private fun setUpAdapter(list: List<Notification>) {
        notificationGeneralAdapter = requireActivity()?.let { NotificationGeneralAdapter(it, list) }
        if (list.isEmpty()) {
            rvListAll.visibility = View.GONE;
            lauout_nodata.visibility = View.VISIBLE
        }
        else {
            lauout_nodata.visibility = View.GONE;
            rvListAll.visibility = View.VISIBLE;

            rvListAll!!.apply {
                setAdapter(notificationGeneralAdapter)
            }
        }
    }
}