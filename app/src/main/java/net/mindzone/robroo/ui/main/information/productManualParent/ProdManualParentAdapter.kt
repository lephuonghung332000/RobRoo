package net.mindzone.robroo.ui.main.information.productManualParent

import android.content.Context
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.adprogressbarlib.AdCircleProgress
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.io.File
import net.mindzone.robroo.R

import android.widget.ProgressBar
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import android.content.pm.PackageManager

import android.content.pm.ResolveInfo

import android.content.Intent
import android.net.Uri
import com.itextpdf.text.pdf.parser.PdfTextExtractor

import com.itextpdf.text.pdf.PdfReader
import java.lang.Exception


class ProdManualParentAdapter(
    var list: ArrayList<Items?>,
    private val context: Context,
    var itemListener: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    fun clearList() {
        list = arrayListOf()
        notifyDataSetChanged()
    }

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
    override fun getItemViewType(position: Int): Int {
        if (list[position] == null) {
            return VIEW_TYPE_LOADING
        } else {
            return  VIEW_TYPE_ITEM
        }
    }
    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var i: Int = 0
       var progressBar:ProgressBar=view.findViewById(R.id.progressBarItem)
        var frameLayout:FrameLayout=view.findViewById(R.id.frameLayout)
        var adCircleProgress: AdCircleProgress = view.findViewById(R.id.dowload_progress)
        var constrainLayout: ConstraintLayout = view.findViewById(R.id.childProductManualDown)
        var btnDowload: TextView = view.findViewById(R.id.btnDowload)
        var txtL308: TextView = view.findViewById(R.id.txtL308)
        var txtTitleM: TextView = view.findViewById(R.id.txtTitleM)
        private var txtTitleManual: TextView = view.findViewById(R.id.txtTitleManualPdfDown)
        var txtLanguage: TextView = view.findViewById(R.id.tvLanguageParent)
        init {
            itemView.setOnClickListener {
                itemListener(position)
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click, "menu_product_manual",0,txtL308.text.toString())
            }
        }
        fun onBind(data: Items) {
            txtL308.text = data.productmodelcode
            txtTitleM.text = data.productgroupnameth
            txtTitleManual.text = data.doctypenameth
            txtLanguage.text = data.doclangnameth
            adCircleProgress.setAdProgress(data.percent)
            when (data.status) {
                1 -> {
                    frameLayout.visibility=View.GONE
                    adCircleProgress.visibility = View.GONE
                    btnDowload.visibility = View.GONE
                    txtL308.visibility = View.VISIBLE
                    txtTitleM.visibility = View.VISIBLE
                    progressBar.visibility=View.GONE
                }
              2 -> {
                    frameLayout.visibility=View.VISIBLE
                    btnDowload.visibility = View.GONE
                    txtL308.visibility = View.VISIBLE
                    txtTitleM.visibility = View.VISIBLE
                    if(data.percent==0){
                        progressBar.visibility=View.VISIBLE
                        adCircleProgress.visibility = View.GONE
                    }
                    else{
                        adCircleProgress.visibility = View.VISIBLE
                        progressBar.visibility=View.GONE
                        adCircleProgress.setAdProgress(data.percent)
                    }
                    adCircleProgress.setOnClickListener(View.OnClickListener {
//                        progressListener(position)
//                        frameLayout.visibility=View.GONE
//                        adCircleProgress.visibility = View.GONE
//                        btnDowload.visibility = View.GONE
//                        txtL308.visibility = View.VISIBLE
//                        txtTitleM.visibility = View.VISIBLE
                    })
                }
               3-> {
                    frameLayout.visibility=View.VISIBLE
                    adCircleProgress.visibility = View.GONE
                   progressBar.visibility=View.GONE
                    txtL308.visibility = View.VISIBLE
                    txtTitleM.visibility = View.VISIBLE
                    btnDowload.visibility = View.VISIBLE
                    btnDowload.text = "ดาวน์โหลดแล้ว"

                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
         if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(
                R.layout.item_product_guide_manual,
                parent,
                false
            )
             return  MyViewHolder(view)
        } else {
             view = LayoutInflater.from(context).inflate(
                 R.layout.item_loading,
                 parent,
                 false
             )
             return  ProgressViewHolder(view)
        }

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder)
        {
            list[position]?.let { holder.onBind(it) }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (position % 2 == 0) {
                    holder.constrainLayout.setBackgroundColor(context.getColor(R.color.white))
                } else {
                    holder.constrainLayout.setBackgroundColor(context.getColor(R.color.color_F5F5F5))
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    fun addLoadingView() {
            list.add(null)
            notifyItemInserted(list.size)
    }
    fun addData(dataViews: List<Items?>) {
            this.list.addAll(dataViews)
            notifyDataSetChanged()
            Log.d("kiki",list.toString())
    }
    fun removeLoadingView() {
            if (list.size != 0) {
                list.removeAt(list.size - 1)
                notifyItemRemoved(list.size - 1)
            }
    }
    fun removeItems(position: Int?) {
        if (position!=null) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    fun getItemList(position: Int):Items?{
        return  this.list[position]
    }
    fun getPositionByUrl(url: String?):Int?{
        val position=this.list.indexOfFirst{(it?.docnumber)==url}
        return position
    }
    fun updateItemByUrl(url: String?, percent: Int){
            val position=this.list.indexOfFirst{(it?.docid.toString()+it?.docnumber+it?.productmodelcode)==url}
            if(position!=-1){
                Log.d("percent",percent.toString())
                if(percent%10==0||percent==1){
                    this.list[position]?.percent=percent
                    notifyItemChanged(position)
                }
            }
    }
    fun updateSuccessUrl(url: String?) {
        list.mapIndexedNotNull { index, elem ->
            if (elem?.docnumber == url)
            {
                notifyItemChanged(index)
            }
            else null }
    }
    fun checkFileCanRead(filePath:String):Boolean{
        try {
            val pdfReader = PdfReader(filePath)
            val textFromPdfFilePageOne = PdfTextExtractor.getTextFromPage(pdfReader, 1)
            return true
        } catch (e: Exception) {
        }
        return false
    }
    fun updateProgress(
        dataViews: List<Items>,
        listName: HashMap<String?, Int?>?,
        listIsLoading: ArrayList<String>?
    ) {
        var flag= arrayListOf<String>()
            for (i in 0 until dataViews.size) {
                val fileName =
                    context.externalCacheDir!!.absolutePath + "/" + dataViews[i].docnumber+".pdf"
                val file = File(fileName)
                if (listIsLoading != null) {
                    for (items in listIsLoading) {
                        if ((dataViews[i].docid.toString() + dataViews[i].docnumber + dataViews[i].productmodelcode) == items) {
                            dataViews[i].status = 2
                            notifyItemChanged(i)
                            continue
                        }
                    }
                }
                if (listName != null) {
                    for (key in listName.keys) {
                        if ((dataViews[i].docid.toString() + dataViews[i].docnumber + dataViews[i].productmodelcode) == key) {
                            dataViews[i].percent = listName[key]!!
                            dataViews[i].status = 2
                            notifyItemChanged(i)
                            continue
                        }
                    }
                }
                if(flag.indexOfFirst {it==fileName}!=-1){
                        dataViews[i].status = 3
                        notifyItemChanged(i)
                        continue
                }
                else if(file.exists() && (dataViews[i].percent == 100||dataViews[i].status == 1)) {
                    if(checkFileCanRead(fileName)){
                        dataViews[i].status = 3
                        flag.add(fileName)
                    }
                    notifyItemChanged(i)
                }

            }
    }
    }
