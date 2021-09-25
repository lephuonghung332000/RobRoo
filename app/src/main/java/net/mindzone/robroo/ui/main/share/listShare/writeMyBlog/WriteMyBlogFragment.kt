package net.mindzone.robroo.ui.main.share.listShare.writeMyBlog

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.lujun.androidtagview.TagView.OnTagClickListener
import dagger.hilt.android.AndroidEntryPoint
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import kotlinx.android.synthetic.main.fragment_write_my_blog.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.view.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveSharef
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelperImp
import net.mindzone.robroo.databinding.FragmentWriteMyBlogBinding
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.share.listShare.ShareFragment
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel
import net.mindzone.robroo.ui.main.share.listShare.viewPager.MyBlogListFragment.Companion.CHECK_UPDATE_OR_INSERT
import net.mindzone.robroo.ui.main.share.listShare.viewPager.MyBlogListFragment.Companion.EXTRA_FORUM_ARTICLE_SAVE_DRAFT
import net.mindzone.robroo.ui.main.share.listShare.viewPager.MyBlogListFragment.Companion.EXTRA_FORUM_GROUP_ID
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class WriteMyBlogFragment() : BaseFragment(R.layout.fragment_write_my_blog) {
    private val PICK_IMAGE_CODE = 0
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE =1
    private lateinit var groupId: String
    private lateinit var azureId: String
    private lateinit var forumArticleSaveDraft: ForumArticleSaveDraft
    private val tagsSelected = arrayListOf<String>()
    private lateinit var insertImageAdapter: InsertImageAdapter
    private var images = ArrayList<String>()
    private var positionImage = 0
    private lateinit var nameUser: String
    private var listUri = arrayListOf<Uri>()
    private  var listImage : ArrayList<Uri>? = null
    private  var listImageString  = mutableListOf<String>()
    private val filePath = java.util.ArrayList<Uri>()
    private var delete = true

    /** true is insert forum article local database, false update */
    private var typeSaveDraft = true

    private val viewModel by viewModels<ShareViewModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"WriteMyBlogFragment",0,"")
        etText.setOnTouchListener(OnTouchListener { v, event ->
            if (etText.hasFocus()) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_SCROLL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })
        initRecycleInsertImage()
        initTagView()
        setEvent()
        getWiriteBlongInLocal()
    }

    private fun setEvent() {
        tvCategory.text =ShareFragment.forumGroups?.title.toString()
       // AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)

    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
        (viewDataBinding as FragmentWriteMyBlogBinding).apply {
            fragmentViewModel = viewModel
        }
        includeCustomToolbar02.txtTile.text = "สร้างกระทู้"
        getDataMyFromBlogListFragment()
        viewModel.title.value = forumArticleSaveDraft.title
        viewModel.text.value = forumArticleSaveDraft.text
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun startObservingValues() {
        viewModel.apply {
            title.observe(viewLifecycleOwner) {
                Log.e("title", it)
                etTitle.error = getErrorEditText(it)
                isRule = !(etTitle.text.isNullOrBlank() || etText.text.isNullOrBlank())
            }
            text.observe(viewLifecycleOwner) {
                Log.e("text", it)
                etText.error = getErrorEditText(it)
                isRule = !(etTitle.text.isNullOrBlank() || etText.text.isNullOrBlank())
            }
            saveDraft.observe(viewLifecycleOwner) {
                if (typeSaveDraft) insertForumArticleSaveDraft(
                    groupId,
                    nameUser,
                    tagsSelected,
                    images
                )
                else {
                    getParametersUpdateForumArticleSaveDraft()
                    updateForumArticleSaveDraft(forumArticleSaveDraft)
                }
                if (isRule) requireActivity().supportFragmentManager.popBackStack()
            }
            eventButtonCreateArticleClick.observe(viewLifecycleOwner) {
                if (isRule) {
                    showLoading()
                    val files = arrayListOf<File>()
                    files.clear()
                    for (position in filePath.indices) {
//                        files.add(convertUriToFile(listUri[position], position))
                        files.add(convertUriToFile(filePath[position], position))
                    }

                    createForumArticle(groupId, nameUser, tagsSelected, files)
                    delete = false
                    deleteForumArticleSaveLocal()

                }
            }
            responseCreateForumArticle.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    setFragmentResult()
                    val forumArticleSaveDraftDelete =
                        arguments?.getSerializable(EXTRA_FORUM_ARTICLE_SAVE_DRAFT) as ForumArticleSaveDraft
                    deleteForumArticleSaveDraft(forumArticleSaveDraftDelete)
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun getParametersUpdateForumArticleSaveDraft() {
        forumArticleSaveDraft.title = viewModel.title.value!!
        forumArticleSaveDraft.text = viewModel.text.value!!
        forumArticleSaveDraft.date = viewModel.getDate()
        forumArticleSaveDraft.tags = tagsSelected
        forumArticleSaveDraft.images = images
    }

    private fun initTagView() {
        tagsSelected.clear()
//        listTagview.tags = viewModel.tags
        listTagview.tags = ShareFragment.forumGroups?.tags
        if (!typeSaveDraft) setTagSelected()
        listTagview.setOnTagClickListener(object : OnTagClickListener {
            override fun onTagClick(position: Int, text: String) {
                listTagview.toggleSelectTagView(position)
                listTagview.getTagView(position).tagSelectedBackgroundColor =
                    Color.parseColor("#00A8A9")
                if (listTagview.getTagView(position).isViewSelected) tagsSelected.add(text)
                else tagsSelected.remove(text)
            }

            override fun onTagLongClick(position: Int, text: String) {}
            override fun onSelectedTagDrag(position: Int, text: String) {}
            override fun onTagCrossClick(position: Int) {}
        })

    }

    /** set tag selected when click on the item of forum article inside local database */
    private fun setTagSelected() {
        tagsSelected.addAll(forumArticleSaveDraft.tags)
        for (tag in forumArticleSaveDraft.tags) {
            for (position in ShareFragment.forumGroups?.tags!!.indices) {
                if (ShareFragment.forumGroups?.tags!![position] == tag) {
                    listTagview.toggleSelectTagView(position)
                    listTagview.getTagView(position).tagSelectedBackgroundColor =
                        Color.parseColor("#00A8A9")
                    break
                }
            }
        }
    }

    private fun initRecycleInsertImage() {
//        val getContent =
//            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//                images[positionImage] = uri.toString()
//                uri?.let { listUri.add(it) }
//                insertImageAdapter.submitList(images.toMutableList())
//            }

        insertImageAdapter = InsertImageAdapter(requireContext(),viewModel, object : InsertImageAdapter.ItemClick{
            override fun onClick() {
                if (checkPermission()){
//                    filePath.clear()
                    FilePickerBuilder.instance.setMaxCount(4)
                        .setSelectedFiles(filePath)
                        .setActivityTheme(R.style.LibAppTheme)
                        .pickPhoto(this@WriteMyBlogFragment, FilePickerConst.REQUEST_CODE_PHOTO)
                }
            }
        })

//            pickImageIntent()
//            getContent.launch("image/*")
//            positionImage = it


//        if (!typeSaveDraft) {
//            images.clear()
//            images.addAll(forumArticleSaveDraft.images)
//        } else {
//            for (i in 0..3) {
//                images.add("$i")
//            }
//        }
//        insertImageAdapter.submitList(images.toMutableList())

        insertImageAdapter.notifyDataSetChanged()


        rvInsertPicture.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = insertImageAdapter
        }
    }



    /** get data from  MyFromBlogListFragment() */
    private fun getDataMyFromBlogListFragment() {
        val user = MainActivity.user
        nameUser = "${user?.firstnameth} ${user?.lastnameth}"
        azureId = viewModel.sharedPreferencesHelper.getCurrentUserId()
        groupId = arguments?.getString(EXTRA_FORUM_GROUP_ID).toString()
        forumArticleSaveDraft =
            arguments?.getSerializable(EXTRA_FORUM_ARTICLE_SAVE_DRAFT) as ForumArticleSaveDraft
        typeSaveDraft = arguments?.getBoolean(CHECK_UPDATE_OR_INSERT) as Boolean

    }

    /** get bit map image with url */
    private fun getBitmap(imageUri: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    requireActivity().contentResolver,
                    imageUri
                )
            )
        } else {
            requireActivity()
                .contentResolver
                .openInputStream(imageUri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
        }
    }

    /** covert from url image to file*/
    private fun convertUriToFile(imageUri: Uri, position: Int): File {
        // create file with name file
        val destinationFile = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "forumarticles_${position}.png"
        )
        //create a file to write bitmap data
        destinationFile.createNewFile()
        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        // get bitmap from uri image
        val bitmap = getBitmap(imageUri)
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, bos)
        val bitmapData = bos.toByteArray()
        //write the bytes in file
        val fos = FileOutputStream(destinationFile)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
        return destinationFile
    }

    /** notification to MyBlogListFragment() have new forum article added*/
    private fun setFragmentResult() {
        requireActivity().supportFragmentManager.setFragmentResult(
            "update forum articles", bundleOf("owner" to "owner")
        )
    }
    private fun pickImageIntent (){
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "selectpickture"), PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FilePickerConst.REQUEST_CODE_PHOTO -> if (resultCode == Activity.RESULT_OK && data != null) {
                filePath.clear()
                filePath.addAll(data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)!!)
                insertImageAdapter.submitList(filePath)
                insertImageAdapter.notifyDataSetChanged()
            }
        }

    }
    private fun checkPermission(): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            return if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (requireActivity()),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    showDialogPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        "readfile",
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE

                    )
                } else {
                    ActivityCompat.requestPermissions(
                        (requireActivity())!!,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                    )
                }
                false
            } else {
                true
            }
        } else {
            return true
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                Toast.makeText(context, "Permistion GRANTED", Toast.LENGTH_LONG).show()
//                getAllImage()
//                setImageList()
            } else {
                Toast.makeText(context, "Permisstion DENIED", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun showDialogPermission(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.apply {
            setMessage("Permission to access you $name is required to use this app")
            setTitle("Permisstion requied")
            setPositiveButton("OK") { dialog, wich ->
                requestPermissions(arrayOf(permission), requestCode)

            }

        }
        val dialog = builder.create()
        dialog.show()

    }
    private fun getWiriteBlongInLocal(){
        var forumAticle : ForumArticleSaveSharef? = viewModel.getWiriteBlongInLocal()
//            etTitle.text = Editable.Factory.getInstance().newEditable(forumAticle?.title)
//            etTitle.setText(forumAticle?.text)
//            etText.setText(forumAticle?.text)
            viewModel.text.value = forumAticle?.text
            viewModel.title.value = forumAticle?.title

    }
    private fun saveWriteBlogToLocal(){
        var forumArticle = ForumArticleSaveSharef()
        forumArticle.title = etTitle.text.toString()
        forumArticle.text =etText.text.toString()
        viewModel.setWriteBlogToLocal(forumArticle)

    }

    override fun onPause() {
        super.onPause()
            if (delete){
            saveWriteBlogToLocal()
          }
    }

    override fun onDetach() {
        super.onDetach()
        hideLoading()
    }

   

}