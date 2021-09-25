package net.mindzone.robroo.ui.main.group

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_manual.*
import kotlinx.android.synthetic.main.fragment_product_manual_parent.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import net.mindzone.robroo.service.MyService
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.ui.main.information.pdfDowloadedParent.PdfBottomSheetDialogManualFragment
import net.mindzone.robroo.ui.main.information.pdfDowloadedParent.PdfDowloadedBottomsheetDialogFragment
import net.mindzone.robroo.ui.main.information.productManualParent.ProdManualParentAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.io.File


@AndroidEntryPoint
class ProductManualFragment : BaseFragment(R.layout.fragment_product_manual), PdfBottomSheetDialogManualFragment.ItemClickListener,
    PdfDowloadedBottomsheetDialogFragment.DeleteClickListener  {
    private val viewModel by viewModels<InformationViewModel>()
    val PERMISSION_REQUEST_CODE = 2296
    var position=0
    var azure_id=MainActivity.user?.azureoid!!
    var list = arrayListOf<Items?>()
    private lateinit var prodManualParentAdapter:ProdManualParentAdapter
    var listName:HashMap<kotlin.String?,Int?>?=null
    val intent: Intent by lazy{
        Intent(context, MyService::class.java)
    }
    var model_id=""

    private var isLoading: Boolean = false
    val azureId = MainActivity.user?.azureoid!!
    var filterList = arrayListOf<Items?>()
    var count=1
    var search=""
    private var myService: MyService? = null
    private var isBound = false
    private lateinit var connection: ServiceConnection
    lateinit var nestedScrollView:NestedScrollView
    fun setLoaded() {
        isLoading = false
    }
    fun getModelnameUrlSafe(model: String):String {
        return model.replace(" ", "%20").replace(",", "%2C")
    }
    override fun onResume() {
        super.onResume()
        manulLayout.requestLayout()
    }
    override fun onStart() {
        super.onStart()
        connection= object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName) {
                isBound = false
            }
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binder: MyService.MyBinder = service as MyService.MyBinder
                myService = binder.service
                isBound = true
            }
        }
        context?.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ProductManualFragment",0,"")
        nestedScrollView= requireActivity().findViewById(R.id.nestedScrollView)
        search= getModelnameUrlSafe(arguments?.getString("id_manual")!!)
        registerReceiver()
        listName= HashMap()
        viewModel.getListDocument(azureId, count, 20,search, "",null,null)
        context?.let {
            prodManualParentAdapter = context?.let {
                ProdManualParentAdapter(filterList, it) { i: Int -> partItemClicked(i) }
            }!!
            recyclerViewProductManual!!.apply {
                setAdapter( prodManualParentAdapter)
                setLayoutManager(LinearLayoutManager(context))
            }
        }
        setRVScrollListener()
    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }
    private  fun setRVScrollListener() {
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY
                    &&isLoading==false) {
                    loadMoreData()
                    isLoading = true
                }
            }
        })
    }
    private fun loadMoreData() {
        prodManualParentAdapter.addLoadingView()
        count=(prodManualParentAdapter.itemCount)
        val countDownTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                viewModel.getListDocument(azureId, count, 20, search, "",null,null)
            } }
        countDownTimer.start()

    }
    companion object {
        fun newInstance(id: String): Fragment {
            val f = ProductManualFragment()
            val b = Bundle()
            b.putString("id_manual", id)
            f.arguments = b
            return f
        }
    }
    override fun startObservingValues() {
        viewModel.apply {
            listDocumentItems.observe(viewLifecycleOwner) {
                if (it==null)
                {
                    if(prodManualParentAdapter.list.size==0){
                        layoutNoDataManual.visibility=View.VISIBLE
                        recyclerViewProductManual.visibility=View.INVISIBLE
                    }
                    else{
                        prodManualParentAdapter.removeLoadingView()
                        prodManualParentAdapter.addData(listOf())
                    }
                } else {
                    layoutNoDataManual.visibility=View.GONE
                    recyclerViewProductManual.visibility=View.VISIBLE
                    setLoaded()
                    if (isBound == true) {
                        if (count > 20) {
                            prodManualParentAdapter.removeLoadingView()
                        }
                        prodManualParentAdapter.updateProgress(
                            it,
                            listName,
                            myService!!.listLoading
                        )
                        prodManualParentAdapter.addData(it)
                    }
                }
            }
        }
    }
    private fun partItemClicked(i: Int) {
        if (prodManualParentAdapter.getItemList(i)?.status == 1) {
            val addBottomDialogFragment = PdfBottomSheetDialogManualFragment()
            val bundle = Bundle()
            bundle.putString("viewOnlineUrl",prodManualParentAdapter.getItemList(i)?.viewonlineurl)
            addBottomDialogFragment.arguments = bundle
            addBottomDialogFragment.show(
                childFragmentManager,
                PdfBottomSheetDialogManualFragment.TAG
            )
            position = i

        } else if (prodManualParentAdapter.getItemList(i)?.status == 3) {
            position = i
            val bottomSheetDialogFragment: BottomSheetDialogFragment =
                PdfDowloadedBottomsheetDialogFragment()
            val bundle = Bundle()
            bundle.putString("viewOnlineUrl",prodManualParentAdapter.getItemList(i)?.viewonlineurl)
            bottomSheetDialogFragment.arguments = bundle
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    private fun registerReceiver() {
        val bManager = LocalBroadcastManager.getInstance(requireContext())
        val intentFilter = IntentFilter()
        intentFilter.addAction("message_progress")
        intentFilter.addAction("message_success")
        bManager.registerReceiver(broadcastReceiver, intentFilter)
    }
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == "message_progress") {
                val b= intent.extras!!
                val url=b.getString("url")
                val percent: Int = b.getInt("percent_download")
                prodManualParentAdapter.updateItemByUrl(url,percent)
                listName?.put(url,percent)
            }
            if(intent.action == "message_success"){
                val b= intent.extras!!
                val docNumber=b.getString("docNumber")
                val fullPath=b.getString("fullPath")
                prodManualParentAdapter.list.mapIndexedNotNull { index, elem ->
                    if (elem?.docnumber == docNumber)
                    {
                        elem?.status=3
                        prodManualParentAdapter.notifyItemChanged(index)
                        docNumber?.let { elem?.let { its -> viewModel.insertData(its) } }
                    }
                    else null }
                listName?.remove(fullPath)
            }
        }
    }
    override fun onStop() {
        super.onStop()
        if (isBound) {
            context?.unbindService(connection)
            isBound = false
        }
    }
    override fun onItemClick(item: String?) {
        intent.putExtra("item",prodManualParentAdapter.getItemList(position))
        if (checkPermission()) {
            context?.startService(intent);
            prodManualParentAdapter.getItemList(position)?.status = 2
            prodManualParentAdapter.notifyItemChanged(position)
        } else {
            requestPermission()
        }
    }
    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result =
                ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            val result1 =
                ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }
    fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", requireContext().packageName))
                startActivityForResult(intent, 2296)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 2296)
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2296) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Allow permission for storage access!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0) {
                val READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Allow permission for storage access!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

        }
    }
    fun updateStatus(url: String?){
        prodManualParentAdapter.list.mapIndexedNotNull { index, elem ->
            if (elem?.docnumber == url)
            {
                elem?.status = 1
                elem?.percent=0
                prodManualParentAdapter.notifyItemChanged(index)
            }
            else null }
    }
    override fun onDeleteClick(item: String?) {
        if (checkPermission()) {
            val url=prodManualParentAdapter.getItemList(position)?.docnumber
            val fileCheckExsistName =
                context?.externalCacheDir!!.absolutePath + "/" +url + ".pdf"
            val file = File(fileCheckExsistName)
            prodManualParentAdapter.getItemList(position)?.let {
                viewModel.deleteData(it.docnumber)
            }
            updateStatus(url)
            if (file.exists()) {
                file.delete()
            }
        } else {
            requestPermission()
        }
    }

    override fun readLinkPdf(): String {
        return context?.externalCacheDir!!.absolutePath + "/" +prodManualParentAdapter.getItemList(position)?.docnumber + ".pdf"
    }
}