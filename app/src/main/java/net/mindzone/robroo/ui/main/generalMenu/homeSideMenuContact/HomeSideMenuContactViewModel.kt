package net.mindzone.robroo.ui.main.generalMenu.homeSideMenuContact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.applicationSetting.entity.Application
import net.mindzone.robroo.data.applicationSetting.repository.ApplicationSettingRepository
import javax.inject.Inject

@HiltViewModel
class HomeSideMenuContactViewModel @Inject constructor(private val applicationSettingRepository: ApplicationSettingRepository) : BaseFragmentVM() {
    private var _applicationSettingResponse = MutableLiveData<Application>()
    val applicationSettingResponse = _applicationSettingResponse

    fun getApplicationSetting (){
        viewModelScope.launch {
            val response = applicationSettingRepository.getApplicationSetting()
            _applicationSettingResponse.value = response.body()!!.responseData.application
        }
    }
}