package net.mindzone.robroo.ui.main.idea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.ListNewsTopmostResponse
import net.mindzone.robroo.data.menu.repository.ListNewsTopMostRepository
import net.mindzone.robroo.data.news.entity.ListByGroupResponse
import net.mindzone.robroo.data.news.entity.newsfeedContent.NewsFeedContentResponse
import net.mindzone.robroo.data.news.repository.ListByGroupRepository
import net.mindzone.robroo.data.news.repository.newsfeedContent.NewsFeedContentRepository
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.utils.extensions.Status
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class IdeaViewModel @Inject constructor(
        private val listNewsTopMostRepository : ListNewsTopMostRepository,
        val sharedPreferencesHelper: SharedPreferencesHelper,
        private val listByGroupRepository: ListByGroupRepository,
        private val newsFeedContentRepository: NewsFeedContentRepository,
) : BaseFragmentVM() {
    var azureId : String = ""
    private var _listNewsTopMostResponse = MutableLiveData<Response<ListNewsTopmostResponse>>()
    val listNewsTopMostResponse: LiveData<Response<ListNewsTopmostResponse>> = _listNewsTopMostResponse

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
    }

    fun getListNewsTopMost(azure_id: String) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _listNewsTopMostResponse.value = listNewsTopMostRepository.getListNewsTopMost(azure_id)
                _status.value = Status.SUCCESS
            } catch (e: Exception) {
                _status.value = Status.ERROR
            }
        }
    }
    private var _listByGroupResponse = MutableLiveData<Response<ListByGroupResponse>>()
    val listByGroupResponse: LiveData<Response<ListByGroupResponse>> = _listByGroupResponse
    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
    }
    fun getListByGroup(azure_id: String,group_id:Int) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _listByGroupResponse.value = listByGroupRepository.getListByGroup(azure_id,group_id)
                _status.value = Status.SUCCESS
            }  catch (e:Exception){
                _status.value = Status.ERROR
            }
        }
    }

    private var _newsFeedContentResponse = MutableLiveData<Response<NewsFeedContentResponse>>()
    val newsFeedContentResponse: LiveData<Response<NewsFeedContentResponse>> = _newsFeedContentResponse
    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
    }

    fun getNewsFeedContent(azure_id: String,content_id:Int) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _newsFeedContentResponse.value = newsFeedContentRepository.getNewsFeedContent(azure_id,content_id)
                _status.value = Status.SUCCESS
            }  catch (e:Exception){
                _status.value = Status.ERROR
            }
        }
    }
}