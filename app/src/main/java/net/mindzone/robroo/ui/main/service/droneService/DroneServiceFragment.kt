import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_drone_service.*
import kotlinx.android.synthetic.main.fragment_product_info.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.ui.main.service.droneService.DroneSeviceAdapter
import net.mindzone.robroo.ui.main.service.itemsService.ItemServiceFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class DroneServiceFragment : BaseFragment(R.layout.fragment_drone_service) {
    private lateinit var droneSeviceAdapter: DroneSeviceAdapter
//    lateinit var mRecyclerView: RecyclerView
//    lateinit var txtTile: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"DroneServiceFragment",0,"")
//        mRecyclerView = view.findViewById(R.id.rvMenu2Drone)
//        txtTile=view.findViewById(R.id.txtTile)
        txtTile.setText(getString(R.string.drone_service))
        txtTile.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            getResources().getDimension(R.dimen.dp_18))
        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.kanit_semibold) }
        txtTile.setTypeface(typeface)
        rvMenu2Drone.layoutManager = LinearLayoutManager(context)
        context?.let {
            droneSeviceAdapter = DroneSeviceAdapter(it) {
                val createFragment = ItemServiceFragment()
                fragmentManager?.beginTransaction()
                    ?.setCustomAnimations(
                        R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right
                    )
                    ?.replace(R.id.frameMain, createFragment)?.addToBackStack("")?.commit()
            }
            rvMenu2Drone.apply {
                adapter = droneSeviceAdapter
            }
        }
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
    }


}