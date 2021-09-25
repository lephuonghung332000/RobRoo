package net.mindzone.robroo.ui.main.information.pdfDowloadedParent

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pdf_dowloaded_parent.*
import net.mindzone.robroo.R
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.ui.main.information.productManualParent.ProdManualParentAdapter
import java.io.File
@AndroidEntryPoint
class PdfDowloadedParentFragment : BaseFragment(R.layout.fragment_pdf_dowloaded_parent),PdfDowloadedBottomsheetDialogFragment.DeleteClickListener {
    private val viewModel by activityViewModels<InformationViewModel>()
    val PERMISSION_REQUEST_CODE = 2296
    var position = 0
    val azureId = MainActivity.user?.azureoid!!
    private var listDowloaded = arrayListOf<Items?>()
    private lateinit var prodManualParentAdapter: ProdManualParentAdapter
    var count=1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            prodManualParentAdapter = context?.let {
                ProdManualParentAdapter(listDowloaded, it) { i: Int -> partItemClicked(i) }
            }!!
            rv_DowloadedParent!!.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = prodManualParentAdapter
            }
        }
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            viewModel.getAllPdf().observe(viewLifecycleOwner){
                prodManualParentAdapter.clearList()
                prodManualParentAdapter.addData(it)
            }
        }
    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
    }
    override fun startObservingValues() {

    }
    private fun partItemClicked(i: Int) {
    if (prodManualParentAdapter.getItemList(i)?.status == 3) {
            position = i
            val bottomSheetDialogFragment: BottomSheetDialogFragment =
                PdfDowloadedBottomsheetDialogFragment()
            val bundle = Bundle()
            bundle.putString("viewOnlineUrl",prodManualParentAdapter.getItemList(i)?.viewonlineurl)
            bottomSheetDialogFragment.arguments = bundle
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
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
        if (requestCode == 2296){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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
    private fun updateStatus(url:String?){
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
                viewModel.deleteData(
                    it.docnumber)
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
        return context?.externalCacheDir!!.absolutePath + "/" +prodManualParentAdapter.getItemList(position)?.docnumber+".pdf"
    }
}
