package net.mindzone.robroo.ui.main.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ListServiceResponse
import net.mindzone.robroo.data.menu.repository.ListTopMostMenuItem.ListServiceRepository
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.utils.extensions.Status
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(

        private val listServiceRepository: ListServiceRepository,
        val sharedPreferencesHelper: SharedPreferencesHelper,
) : BaseFragmentVM() {
    var azureId : String = ""
    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status
    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
        Log.d("asdfadsfa", azureId)
    }
    fun setStatus(){
        _status.value = Status.SUCCESS
    }
    private var _listServiceResponse = MutableLiveData<Response<ListServiceResponse>>()
    val listServiceResponse: LiveData<Response<ListServiceResponse>> = _listServiceResponse
    fun getListService(id:String) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _listServiceResponse.value = listServiceRepository.getListService(azureId,id)
                Log.e("herse", "===========")
                _status.value = Status.SUCCESS
            } catch (e: Exception) {
                _status.value = Status.ERROR
            }
        }
    }
}