package net.mindzone.robroo.ui.main.share.mainMenu4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumGroups
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumListResponse
import net.mindzone.robroo.data.forumArticle.repository.ForumArticleRepository
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.utils.extensions.Status
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class Menu4MainViewModel @Inject constructor(
    private val forumArticleRepository: ForumArticleRepository
) : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status
    private var _forumListResponse = MutableLiveData<Response<ForumListResponse>>()
    val forumListResponse: LiveData<Response<ForumListResponse>> = _forumListResponse
    fun getForumList() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _forumListResponse.value = forumArticleRepository.getForumList()
                _status.value = Status.SUCCESS
            }
            catch (e: Exception) {
                _status.value = Status.ERROR
            }
        }
    }
}