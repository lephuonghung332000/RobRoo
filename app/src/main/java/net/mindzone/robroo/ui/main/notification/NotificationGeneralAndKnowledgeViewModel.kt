package net.mindzone.robroo.ui.main.notification


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.notification.entity.Notification
import net.mindzone.robroo.data.notification.repository.NotificationRepository
import net.mindzone.robroo.utils.extensions.Status
import javax.inject.Inject

@HiltViewModel
class NotificationGeneralAndKnowledgeViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : BaseFragmentVM() {
    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status
    private var _notificationResponse = MutableLiveData<List<Notification>>()
    val notificationResponse: LiveData<List<Notification>> = _notificationResponse

    fun getNotifications(type: String, azure_id: String, page: Int) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val response = notificationRepository.getNotification(type, azure_id, page)
                _notificationResponse.value = response.body()?.responseData?.notifications
                _status.value = Status.SUCCESS
//                while (response.isSuccessful){
//
//                    Log.e("notification", "da load xong")
//                    break
//                }


            } catch (e: Exception) {
                _status.value = Status.ERROR
//                _notificationResponse.value = null
            }
        }

    }

}