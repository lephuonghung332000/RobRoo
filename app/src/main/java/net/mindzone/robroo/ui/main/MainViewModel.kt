package net.mindzone.robroo.ui.main

import android.app.Notification
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.base.BaseActivityVM
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.data.token.repository.TokenRepository

import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val tokenRepository: TokenRepository,val sharedPreferencesHelper: SharedPreferencesHelper) : BaseActivityVM() {
    private var _pushTokenResponse = MutableLiveData<List<Notification>>()
    val pushTokenResponse: LiveData<List<Notification>> = _pushTokenResponse

    fun setPushToken(token: String,azure_id: String) {

        viewModelScope.launch {

            var enabled = sharedPreferencesHelper.getStatusNotification()
            val response = tokenRepository.setPushToken(token,azure_id, "ANDROID", enabled)

        }
    }
}