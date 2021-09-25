package net.mindzone.robroo.ui.main.information.productCategory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.filter_bottom_sheet_menu2.*
import net.mindzone.robroo.R
import net.mindzone.robroo.data.productgroupListproductmodel.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationViewModel

@AndroidEntryPoint
class FilterBottomSheetDialog  : BottomSheetDialogFragment() {
    private val viewModel by viewModels<InformationViewModel>()
    private lateinit var filterBottomSheetAdapter: FilterBottomSheetAdapter
     var list : List<ResponseData> = listOf()
    val azureId = MainActivity.user!!.azureoid
    private var mListener: FilterClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.filter_bottom_sheet_menu2, container, false)
        val id=arguments?.getInt("IdtittleSub")!!
        viewModel.getListProductModel(azureId, id)
        startObservingValue()
        return view
    }

    private fun startObservingValue() {
        viewModel.apply {
            listProductModelResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null){
                        setAdapter(it.body()?.responseData!!)
                    }
                    else{
//                        rvMenu2ProductCategory.visibility = View.GONE
//                        layoutNoDataI2.visibility = View.VISIBLE
                    }
                } else {

                }
            }
        }
        }

    private fun setAdapter(responseData: List<ResponseData>) {
        context?.let {
            filterBottomSheetAdapter = FilterBottomSheetAdapter(responseData,it,itemListener = {
                mListener?.onFilterClick(it)
                dismiss()
            })
            list_filterCategory!!.apply {
                adapter = filterBottomSheetAdapter
                layoutManager = LinearLayoutManager(context)
//                setVeilLayout(R.layout.cell_skeleton)
//                addVeiledItems(7)
            }

        }
    }

    companion object {
        fun newInstance(id: Int): FilterBottomSheetDialog {
            val f = FilterBottomSheetDialog()
            val b = Bundle()
            b.putInt("IdtittleSub", id)
            f.arguments = b
            return f
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = parentFragment as FilterClickListener?
        } catch (e: Exception) {
            //handle exception
        }
    }
    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
    interface FilterClickListener {
        fun onFilterClick(item: String)
    }

}


