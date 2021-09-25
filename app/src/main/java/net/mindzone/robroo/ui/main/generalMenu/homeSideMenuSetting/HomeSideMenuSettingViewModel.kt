package net.mindzone.robroo.ui.main.generalMenu.homeSideMenuSetting

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.data.token.repository.TokenRepository
import javax.inject.Inject

@HiltViewModel
class HomeSideMenuSettingViewModel @Inject constructor(private val tokenRepository: TokenRepository, val sharedPreferencesHelper: SharedPreferencesHelper): BaseFragmentVM() {
    fun setStatusSwitch(id: String){
        viewModelScope.launch {
            sharedPreferencesHelper.setStatusNotification(id)
        }
    }
    fun setPushToken(token: String,azure_id: String) {

        viewModelScope.launch {
            var status = sharedPreferencesHelper.getStatusNotification()
            Log.d("check status",sharedPreferencesHelper.getStatusNotification() )
            val response = tokenRepository.setPushToken(token,azure_id, "ANDROID", status)
        }
    }
    fun getStatusSwitch() : String = sharedPreferencesHelper.getStatusNotification()

    }

