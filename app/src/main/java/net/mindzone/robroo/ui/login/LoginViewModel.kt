package net.mindzone.robroo.ui.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.data.user.entity.ResponseData
import net.mindzone.robroo.data.user.repository.UserRepository
import net.mindzone.robroo.utils.Event
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val sharedPreferencesHelper: SharedPreferencesHelper
) :
    ViewModel() {

    private val _navigateToLogin = MutableLiveData<Event<Unit>>()
    val navigateToLogin: LiveData<Event<Unit>> = _navigateToLogin

    private val _navigateToStartActivity = MutableLiveData<Event<Unit>>()
    val navigateToStartActivity: LiveData<Event<Unit>> = _navigateToStartActivity

    init {
        if (!sharedPreferencesHelper.getCurrentUserId().isNullOrEmpty()) {
            login(sharedPreferencesHelper.getCurrentUserId())

        }
    }

    fun navigateToLogin() {
        _navigateToLogin.value = Event(Unit)
    }

    private var _responeData = MutableLiveData<ResponseData>()
    val user: LiveData<ResponseData> = _responeData

    fun login(azureId: String) {
        sharedPreferencesHelper.setCurrentUserId(azureId)
        viewModelScope.launch {
            try {
            val response =
                userRepository.login(azureId)
            _responeData.value = response.body()?.responseData
            if (response.isSuccessful) {
                _navigateToStartActivity.value = Event(Unit)
            }
        }catch (e: Exception){
            Toast.makeText(RobRooApplication.applicationContext(), "Check Your Internet Connection", Toast.LENGTH_LONG).show()
        }  }
    }

}