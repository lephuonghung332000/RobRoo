package net.mindzone.robroo.ui.main.share.listShare

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.ResponseCode
import net.mindzone.robroo.data.forumArticle.entity.comment.Comment
import net.mindzone.robroo.data.forumArticle.entity.comment.ForumArticleCommentsResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticle
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveSharef
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticlesResponse
import net.mindzone.robroo.data.forumArticle.repository.ForumArticleRepository
import net.mindzone.robroo.data.sharedpref.sharePreferencesComment.SharedPreferencesCommentHelper
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.share.listShare.viewPager.listAll.ListAllFragment
import net.mindzone.robroo.ui.main.share.listShare.writeMyBlog.WriteMyBlogFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.Event
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
     val forumArticleRepository: ForumArticleRepository,
    val sharedPreferencesHelper: SharedPreferencesHelper, val sharedPreferencesCommentHelper: SharedPreferencesCommentHelper
) : ViewModel() {
    private  var count : Int= 0
    private lateinit var navigator: ShareNavigator

    fun setNavigator(navigator: ShareNavigator) {
        this.navigator = navigator
    }
    /*<------------------------------------------------------>*/

    // navigate to WriteMyBlogFragment
    private val _navigateToWriteMyBlog = MutableLiveData<Event<Unit>>()
    val navigateToWriteMyBlog: LiveData<Event<Unit>> = _navigateToWriteMyBlog
    fun navigateToWriteMyBlog() {
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
        _navigateToWriteMyBlog.value = Event(Unit)
    }

    /* <--------------model ShareFragment-------------------> */

    val toggleButtonFragmentShare = MutableLiveData<Boolean>().apply { postValue(true) }
    fun toggleListAllAndMyBlog(boolean: Boolean) {
        if (boolean) {
            toggleButtonFragmentShare.value = true
            navigator.onClickToggle("ListAllFragment")
        } else {
            toggleButtonFragmentShare.value = false
            navigator.onClickToggle("MyBlogListFragment")
        }
    }

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    private val _handleFilterList = MutableLiveData<Event<Unit>>()
    val handleFilterList: LiveData<Event<Unit>> = _handleFilterList
    fun handleFilterList() {
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
        _handleFilterList.value = Event(Unit)
    }

    fun filter() : MutableLiveData<String>{
        AuditManager.instance.track(
            RobRooApplication.applicationContext(),
            AuditType.click,
            this::class.java.simpleName,
            0,
            this::class.qualifiedName
        )
       return MutableLiveData<String>()
    }
    /*<------------------------------------------------------------------------->*/

    // model ListAllFragment
    private var _forumArticlesResponse = MutableLiveData<Response<ForumArticlesResponse>>()
    val forumArticlesResponse: LiveData<Response<ForumArticlesResponse>> = _forumArticlesResponse
    fun getForumArticles(
        group_id: String,
        searchQuery: String,
        page: Int,
        azure_id: String,
        sort: String,
        owner: Int,
    ) {
        viewModelScope.launch {
            _forumArticlesResponse.value = forumArticleRepository.getForumArticles(
                group_id,
                searchQuery,
                page,
                azure_id,
                sort,
                owner
            )
        }
    }




    //  <--------------model ListDetailFragment (detail forum article) ------------>

    var tvLikeForumArticle = MutableLiveData<String>()
    var toggleIconLike = MutableLiveData<Boolean>().apply { postValue(true) }

    var isLiked = MutableLiveData<String>()
    var isLikedToggle = MutableLiveData<String>()
    fun handleClickLikeForumArticle(forumArticle: ForumArticle) {
        
        if (isLiked.value == "1") {
            if (isLikedToggle.value == "1") {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                toggleIconLike.value = false
                isLikedToggle.value = "0"
                tvLikeForumArticle.value = (forumArticle.likes - 1).toString()
            } else {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)

                toggleIconLike.value = true
                isLikedToggle.value = "1"
                tvLikeForumArticle.value = forumArticle.likes.toString()
            }
        } else {
            if (isLikedToggle.value == "1") {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                toggleIconLike.value = false
                isLikedToggle.value = "0"
                tvLikeForumArticle.value = forumArticle.likes.toString()
            } else {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                toggleIconLike.value = true
                isLikedToggle.value = "1"
                tvLikeForumArticle.value = (forumArticle.likes + 1).toString()
            }
        }
        viewModelScope.launch {
            forumArticleRepository.likeForumArticle(
                forumArticle.articleId,
                sharedPreferencesHelper.getCurrentUserId(),
                isLiked.value.toString()
            )
        }
    }

    val toggleButtonInListDetail = MutableLiveData<Boolean>().apply { postValue(true) }

    fun isButtonRelatedTagsClick() {
        toggleButtonInListDetail.value = true
    }

    var isScrollTopOrBottomListComment = MutableLiveData<Boolean>()

    private var _isButtonCommentClick = MutableLiveData<Event<Unit>>()
    val isButtonCommentClick: LiveData<Event<Unit>> = _isButtonCommentClick

    fun isButtonCommentClick(forumArticle: ForumArticle, isClick: Boolean) {
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
        getForumArticleComments(forumArticle.articleId, forumArticle.azureId)
        isScrollTopOrBottomListComment.value = isClick
        toggleButtonInListDetail.value = false
        if (isClick) _isButtonCommentClick.value = Event(Unit)
    }


    private var _forumArticleResponse = MutableLiveData<Response<ForumArticleResponse>>()
    val forumArticleResponse: LiveData<Response<ForumArticleResponse>> = _forumArticleResponse

    fun getForumArticle(article_id: String, azure_id: String) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _forumArticleResponse.value =
                    forumArticleRepository.getForumArticle(article_id, azure_id)
                _status.value = Status.SUCCESS
            }catch (e: Exception) {
                _status.value = Status.ERROR
            }

        }
    }

    private var _commentsResponse = MutableLiveData<Response<ForumArticleCommentsResponse>>()
    val commentsResponse: LiveData<Response<ForumArticleCommentsResponse>> = _commentsResponse

    fun getForumArticleComments(article_id: String, azure_id: String) {
        viewModelScope.launch {
            _commentsResponse.value =
                forumArticleRepository.getForumArticleComments(article_id, azure_id)
            count = _commentsResponse.value!!.body()!!.responseData.comments.size
        }
    }

    var doneComment = MutableLiveData<Boolean>()
    val textComment = MutableLiveData<String>()
    fun clickComment(forumArticle: ForumArticle){
        val user = MainActivity.user
        val author = "${user?.firstnameth} ${user?.lastnameth}"
        if (textComment.value!!.isNotBlank())
            viewModelScope.launch {
                doneComment.value = false
                val responseCreateComment = forumArticleRepository.createForumArticleComment(
                    forumArticle.articleId,
                    sharedPreferencesHelper.getCurrentUserId(),
                    "0",
                    textComment.value.toString(),
                    author
                )
                if (responseCreateComment.isSuccessful) {
                    isButtonCommentClick(forumArticle, false)
                    doneComment.value = true
                }
            }
    }
    fun onClickComment(forumArticle: ForumArticle) {
        val user = MainActivity.user
        val author = "${user?.firstnameth} ${user?.lastnameth}"
        if (textComment.value!!.isNotBlank())
            viewModelScope.launch {
                val responseCreateComment = forumArticleRepository.createForumArticleComment(
                    forumArticle.articleId,
                    sharedPreferencesHelper.getCurrentUserId(),
                    "0",
                    textComment.value.toString(),
                    author
                )
                if (responseCreateComment.isSuccessful) {
                    isButtonCommentClick(forumArticle, false)
                }
            }

//        sharedPreferencesComentHelper.deleteSharePrefComment(forumArticle.articleId)

    }



    fun getCountComment(): String {
        return "ความคิดเห็นที่ ${count--}"
    }

    fun checkLikedComment(comment: Comment) = comment.isLiked == 1

    val textReplyComment = MutableLiveData<String>().apply { postValue("") }

    fun onClickReplyComment(comment: Comment) {
        val user = MainActivity.user
        val author = "${user?.firstnameth} ${user?.lastnameth}"
        val forumArticle = forumArticleResponse.value?.body()?.responseData?.forumArticle!!
        if (textReplyComment.value!!.isNotBlank())
            viewModelScope.launch {
                val responseCreateComment = forumArticleRepository.createForumArticleComment(
                    forumArticle.articleId,
                    sharedPreferencesHelper.getCurrentUserId(),
                    comment.commentId,
                    textReplyComment.value.toString(),
                    author
                )
                if (responseCreateComment.isSuccessful) {
                    isButtonCommentClick(forumArticle, false)
                }
            }
    }

    fun handleClickLikeComment(comment: Comment) {
        viewModelScope.launch {
            val isLiked = if (comment.isLiked == 0) "1" else "0"
            val articleId =
                forumArticleResponse.value?.body()?.responseData?.forumArticle?.articleId
            if (articleId != null) {
                val response = forumArticleRepository.likeForumArticleComment(
                    articleId,
                    sharedPreferencesHelper.getCurrentUserId(),
                    isLiked,
                    comment.commentId
                )
                if (response.isSuccessful)
                    getForumArticleComments(articleId, sharedPreferencesHelper.getCurrentUserId())
            }
        }
    }

    /*----------------------------------------------------------------------->*/

    /* MyBlogListFragment */



    /** delete forrumArticle */
     fun deleteForumArticler(azure_id: String,article_id: String){
        viewModelScope.launch {
            forumArticleRepository.deleteForumArticle(azure_id,article_id)
        }
    }

//    val tags: MutableList<String> = ArrayList()

//    init {
//        tags.addAll(
//            listOf(
//                "แท็กที่เกี่ยว",
//                "แท็กที่เกี่ยวข้อง",
//                "แท็กที่",
//                "แท็กที่เกี่ยวข้อง",
//                "แท็กที่เกี่ยว",
//                "แท็กที่เกี่ยว",
//                "แท็กที่เกี่ยว",
//                "แท็กที่เกี่ยวข้อง"
//            )
//        )
//    }

    /** two way binding EditText title, listener event change EditText */
    var title = MutableLiveData<String>()

    /** two way binding EditText text, listener event change EditText*/
    var text = MutableLiveData<String>()

    /** condition insert forum article into local database */
    var isRule = false

    /** set ruler for edit text */
    fun getErrorEditText(text: String): String? {
        Log.e("text", text)
        return if (text.isBlank()) {
            "Text not blank!"
        } else {
            null
        }
    }

    private var _saveDraft = MutableLiveData<Event<Unit>>()
    val saveDraft: LiveData<Event<Unit>> = _saveDraft

    /** click button save draft, save forum article into database local */
    fun saveDraft() {
        _saveDraft.value = Event(Unit)
    }

    /** fun insert ForumArticleSaveDraft into database local */
    fun insertForumArticleSaveDraft(
        groupId: String,
        author: String,
        tags: ArrayList<String>,
        images: ArrayList<String>
    ) {
        val date = getDate()
        if (isRule) {
            val forumArticleSaveDraft = ForumArticleSaveDraft(
                groupId = groupId,
                title = title.value!!,
                text = text.value!!,
                author = author,
                azureId = sharedPreferencesHelper.getCurrentUserId(),
                tags = tags,
                date = date,
                read = 0,
                commentCount = 0,
                viewCount = 0,
                images = images
            )
            viewModelScope.launch {
                forumArticleRepository.insertForumArticleSaveDraft(forumArticleSaveDraft)
            }
        }
    }

    /** get date time format */
    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        } else {
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        }
    }

    /** fun update forum article save draft local database*/
    fun updateForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft) {
        viewModelScope.launch {
            if (isRule) {
                forumArticleRepository.updateForumArticleSaveDraft(forumArticleSaveDraft)
//                getForumArticlesSaveDraft(forumArticleSaveDraft.groupId)
            }
        }
    }

    /** fun delete forum article save draft local database*/
    fun deleteForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft) {
        viewModelScope.launch {
            forumArticleRepository.deleteForumArticleSaveDraft(forumArticleSaveDraft)
        }
    }

    private var _eventButtonCreateArticleClick = MutableLiveData<Event<Unit>>()
    val eventButtonCreateArticleClick: LiveData<Event<Unit>> = _eventButtonCreateArticleClick
    fun isButtonCreateForumArticleClick() {
        _eventButtonCreateArticleClick.value = Event(Unit)

    }

    var responseCreateForumArticle = MutableLiveData<Response<ResponseCode>>()

    /** create forum article into database server*/
    fun createForumArticle(
        groupId: String,
        author: String,
        tagsSelected: ArrayList<String>,
        files: ArrayList<File>
    ) {
        val azureIdRB = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            sharedPreferencesHelper.getCurrentUserId()
        )

        val titleRB = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            title.value!!
        )

        val textRB = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            text.value!!
        )

        val authorRB = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            author
        )

        val groupIdRB = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            groupId
        )

        val tags = arrayListOf<MultipartBody.Part>()
        if (!tagsSelected.isNullOrEmpty()) {
            for (tag in tagsSelected) {
                tags.add(MultipartBody.Part.createFormData("tags[]", tag))
            }
        } else {
            tags.add(MultipartBody.Part.createFormData("tags[]", String()))
        }

        val imagesUpload = arrayListOf<MultipartBody.Part>()
        if (!files.isNullOrEmpty()) imagesUpload.addAll(covertFilesToMultipartBodyParts(files))
        else {
            imagesUpload.add(MultipartBody.Part.createFormData("images[]", "[]"))
        }

        viewModelScope.launch {
            responseCreateForumArticle.value = forumArticleRepository.createForumArticle(
                azureIdRB,
                tags,
                imagesUpload,
                titleRB,
                textRB,
                groupIdRB,
                authorRB
            )
        }
    }

    //* covert files to MultiPart.Body, return List<MultipartBody.Part> */
    private fun covertFilesToMultipartBodyParts(files: ArrayList<File>): List<MultipartBody.Part> {
        val imagesUpload = arrayListOf<MultipartBody.Part>()
        for (file in files) {
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("images[]", file.name, requestFile)
            imagesUpload.add(body)
        }
        return imagesUpload
    }
     fun setWriteBlogToLocal(forumArticle: ForumArticleSaveSharef){
        viewModelScope.launch {
            sharedPreferencesHelper.setForumArticleSaveLocal(forumArticle)
        }

    }
     fun getWiriteBlongInLocal() : ForumArticleSaveSharef {

         return sharedPreferencesHelper.getForumArticleSaveLocal()
    }
    fun deleteForumArticleSaveLocal(){
        sharedPreferencesHelper.deleteForumArticleSaveLocal()
    }
}