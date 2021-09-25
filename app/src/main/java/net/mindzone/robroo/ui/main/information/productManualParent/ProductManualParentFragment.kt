package net.mindzone.robroo.ui.main.information.productManualParent
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.os.Build.VERSION.SDK_INT
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding.widget.RxTextView
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_category03.*
import kotlinx.android.synthetic.main.fragment_product_manual_parent.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import net.mindzone.robroo.data.manualDocument.entity.listLanguage.ResponseData
import net.mindzone.robroo.service.MyService
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.FilterShowingProductManualBottomDialog
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.ui.main.information.pdfDowloadedParent.PdfBottomSheetDialogManualFragment
import net.mindzone.robroo.ui.main.information.pdfDowloadedParent.PdfDowloadedBottomsheetDialogFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.OnLoadMoreListener
import net.mindzone.robroo.utils.RecyclerViewLoadMoreScroll
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager
import rx.android.schedulers.AndroidSchedulers
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.String as String1


@AndroidEntryPoint
class ProductManualParentFragment : BaseFragment(R.layout.fragment_product_manual_parent), PdfBottomSheetDialogManualFragment.ItemClickListener ,FilterShowingProductManualBottomDialog.FilterClickListener,PdfDowloadedBottomsheetDialogFragment.DeleteClickListener {
    private val viewModel by viewModels<InformationViewModel>()
    var i=0
    var loadFirst=0
    var listName:HashMap<kotlin.String?,Int?>?=null
    val PERMISSION_REQUEST_CODE = 2296
    val intent: Intent by lazy{
        Intent(context, MyService::class.java)
    }
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var btnBackManual02: TextView
    var position = 0
    var search = ""
    val azureId = MainActivity.user?.azureoid!!
    var posType=0
    var posLanguage=0
    var filter = ""
    var flag=false
    var filterList = arrayListOf<Items?>()
    var list = arrayListOf<Items?>()
    var count=1
    var listType =
        listOf<net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ResponseData>()
    var listLanguage = listOf<ResponseData>()
    lateinit var spinnerCategory: Spinner
    lateinit var spinnerLanguage: Spinner
    lateinit var imgTypeGuide: ImageView
    private lateinit var prodManualParentAdapter: ProdManualParentAdapter
    private var myService: MyService? = null
    private var isBound = false
    private lateinit var connection:ServiceConnection
    lateinit var runnable:Runnable
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if(i>0){
                prodManualParentAdapter.list.mapIndexedNotNull { index, elem ->
                    val url=elem?.docnumber
                    val fileCheckExsistName =
                        context?.externalCacheDir!!.absolutePath + "/" +url + ".pdf"
                    val file = File(fileCheckExsistName)
                    if (!file.exists()&&elem?.status==3)
                    {
                        elem.status = 1
                        elem.percent =0
                        prodManualParentAdapter.notifyItemChanged(index)
                    }
                    else null }
                }
        }
    }
    fun getModelnameUrlSafe(model: kotlin.String): kotlin.String {
        return model.replace(" ", "%20").replace(",", "%2C")
    }
    override fun onStop() {
        super.onStop()
        if (isBound) {
            context?.unbindService(connection)
            isBound = false
        }
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ProductManualParentFragment",0,"")
        i++
        registerReceiver()
        listName= HashMap()
        btnBackManual02 = view.findViewById(R.id.btnBackManual02)
        imgTypeGuide = view.findViewById(R.id.imgGuideType)
        rv_ManualParent!!.apply {
            layoutManager = LinearLayoutManager(context)
        }
        spinnerCategory = view.findViewById(R.id.spinnerCatgory)
        spinnerLanguage = view.findViewById(R.id.spinnerLanguage)
        setEventClick()
        viewModel.getListLanguage(azureId)
        viewModel.getListDocumentType(azureId)
        context?.let {
            prodManualParentAdapter = context?.let {
                ProdManualParentAdapter(filterList, it) { i: Int -> partItemClicked(i) }
            }!!
            rv_ManualParent!!.apply {
                adapter = prodManualParentAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        setRVScrollListener()
    }
    private  fun setRVScrollListener() {
        mLayoutManager = LinearLayoutManager(context)
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
        rv_ManualParent.addOnScrollListener(scrollListener)
    }
    fun queryEvent(){
        search=getModelnameUrlSafe(search)
        if(posLanguage!=0&&posType==0)
            viewModel.getListDocument(azureId, count, 20, search, filter,posLanguage,null)
        else if(posType!=0&&posLanguage==0){
            viewModel.getListDocument(azureId, count, 20, search, filter,null,posType) }
        else if(posLanguage!=0&&posType!=0){
            viewModel.getListDocument(azureId, count, 20, search, filter,posLanguage,posType) }
        else{
            viewModel.getListDocument(azureId, count, 20, search, filter,null,null)
        }
    }
    private fun loadMoreData() {
        runnable= Runnable { queryEvent() }
        prodManualParentAdapter.addLoadingView()
        count=(prodManualParentAdapter.itemCount)
        val countDownTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                queryEvent()
            } }
        countDownTimer.start()
    }
    private fun setEventClick() {
        imgTypeGuide.setOnClickListener {
            val bottomSheetDialogFragment: BottomSheetDialogFragment =
                FilterShowingProductManualBottomDialog()
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,"imgTypeGuide")
        }
            btnBackManual02.setOnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,"btnBackManual02")
                search_bar_parent.clearFocus()
                search_bar_parent.setText("")
                spinnerCategory.setSelection(0)
                spinnerLanguage.setSelection(0)
                posLanguage=0
                posType=0
                filter=""
            }
        RxTextView.afterTextChangeEvents(search_bar_parent)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tvChangeEvent: TextViewAfterTextChangeEvent ->
                search = tvChangeEvent.view().text.toString()
                prodManualParentAdapter.clearList()
                count=1
                if(search==""){
                    layoutTitleScreenDisplay.visibility = View.VISIBLE
                    btnBackManual02.visibility = View.GONE
                    queryEvent()
                }
                else{
                    btnBackManual02.visibility = View.VISIBLE
                    layoutTitleScreenDisplay.visibility = View.GONE
                    queryEvent()
                }
            }
    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
    }
    override fun startObservingValues() {
        viewModel.apply {

            listDocumentItems.observe(viewLifecycleOwner){
                if(it==null){
                    if(prodManualParentAdapter.list.size==0){
                        layoutNoDataI12.visibility=View.VISIBLE
                        rv_ManualParent.visibility=View.INVISIBLE
                    }
                    else if(loadFirst>0){
                        prodManualParentAdapter.removeLoadingView()
                        prodManualParentAdapter.addData(listOf())
                    }
                }
                else{
                    layoutNoDataI12.visibility=View.GONE
                    rv_ManualParent.visibility=View.VISIBLE
                    loadFirst++
                    scrollListener.setLoaded()
                    if(isBound==true){
                        if(count>20){
                            prodManualParentAdapter.removeLoadingView()
                        }
                        prodManualParentAdapter.updateProgress(it, listName, myService!!.listLoading)
                        prodManualParentAdapter.addData(it)
                    }
                }
            }
            listLanguageResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        listLanguage = it.body()?.responseData!!
                        setAdapter2(listLanguage)
                    }
                }
            }
            listDocumentTypeResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        listType = it.body()?.responseData!!
                        setAdapter1(listType)
                    }
                }
            }
            status.observe(viewLifecycleOwner) {
                when (it) {
                    Status.LOADING -> {
                        if(prodManualParentAdapter.list.isEmpty()){//if list empty
                            rv_ManualParent.visibility=View.INVISIBLE
                            progressBarManual.visibility=View.VISIBLE
                            layoutNoDataI12.visibility=View.GONE
                        }
                    }
                    Status.SUCCESS -> {
                        rv_ManualParent.visibility=View.VISIBLE
                        progressBarManual.visibility=View.GONE
                    }
                }
            }
        }
    }
    private fun setAdapter1(listType: List<net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ResponseData>) {
        val arrayListType= arrayListOf<net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ResponseData>()
        arrayListType.addAll(listType)
        arrayListType.add(0, net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ResponseData(typenameth = getString(R.string.categorySpinner)))
        val adapter01 = context?.let {
            ArrayAdapter(it, R.layout.support_spinner_item, arrayListType)
        }
        adapter01?.setDropDownViewResource(R.layout.support_spinner_item)
        spinnerCategory.adapter = adapter01
        spinnerCategory.setSelection(0,false)
        spinnerCategory.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                    if (position > 0)
                        posType = listType[position - 1].typeid
                    else {
                        posType = 0
                    }
                    count = 1
                    prodManualParentAdapter.clearList()
                    queryEvent()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
    private fun setAdapter2(listLanguage: List<ResponseData>) {
        val arrayListLanguage= arrayListOf<ResponseData>()
        arrayListLanguage.addAll(listLanguage)
        arrayListLanguage.add(0, ResponseData(langnameth = getString(R.string.languageSpinner)))
        val adapter02 = context?.let {
            ArrayAdapter(it, R.layout.support_spinner_item, arrayListLanguage)
        }
        adapter02?.setDropDownViewResource(R.layout.support_spinner_item)
        spinnerLanguage.adapter = adapter02
        spinnerLanguage.setSelection(0,false)
        spinnerLanguage.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                    if (position > 0) {
                        posLanguage = listLanguage[position - 1].langid
                    } else {
                        posLanguage = 0
                    }
                    count = 1
                    prodManualParentAdapter.clearList()
                        queryEvent()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }
    private fun partItemClicked(i: Int) {
        if (prodManualParentAdapter.getItemList(i)?.status == 1) {
            val addBottomDialogFragment = PdfBottomSheetDialogManualFragment()
            val bundle = Bundle()
            bundle.putString("viewOnlineUrl", prodManualParentAdapter.getItemList(i)?.viewonlineurl)
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
            bundle.putString("viewOnlineUrl", prodManualParentAdapter.getItemList(i)?.viewonlineurl)
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
    override fun onItemClick(item: String1?) {
        intent.putExtra("item", prodManualParentAdapter.getItemList(position))
        if (checkPermission()) {
                context?.startService(intent);
                prodManualParentAdapter.getItemList(position)?.status = 2
                prodManualParentAdapter.notifyItemChanged(position)
        }
        else {
            requestPermission()
        }
    }
    private fun checkPermission(): Boolean {
        return if (SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result =
                ContextCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE)
            val result1 =
                ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }
    fun requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String1.format("package:%s", requireContext().packageName))
                startActivityForResult(intent, 2296)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 2296)
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(WRITE_EXTERNAL_STORAGE),
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
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
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
        permissions: Array<out String1>,
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
    override fun onFilterClick(item: String1) {
        when (item) {
            "lastest" -> {
                filter = item
            }
            "popular" -> {
                filter = item
            }
        }
        prodManualParentAdapter.clearList()
        count=1
        queryEvent()
    }
    fun updateStatus(url: kotlin.String?){
        prodManualParentAdapter.list.mapIndexedNotNull { index, elem ->
            if (elem?.docnumber == url)
            {
                elem?.status = 1
                elem?.percent=0
                prodManualParentAdapter.notifyItemChanged(index)
            }
            else null }
    }
    override fun onDeleteClick(item: String1?) {
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
    override fun readLinkPdf(): String1 {
        return context?.externalCacheDir!!.absolutePath + "/" +prodManualParentAdapter.getItemList(position)?.docnumber + ".pdf"
    }
}

