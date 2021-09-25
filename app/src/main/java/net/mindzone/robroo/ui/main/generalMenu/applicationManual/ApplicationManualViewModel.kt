package net.mindzone.robroo.ui.main.generalMenu.applicationManual

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.faqList.entity.FaqList
import net.mindzone.robroo.data.faqList.repository.FaqListRepository
import javax.inject.Inject

@HiltViewModel
class ApplicationManualViewModel @Inject constructor(private val faqListRepository: FaqListRepository) : BaseFragmentVM() {
    private var _faqListResponse = MutableLiveData<List<FaqList>>()
    val faqListResponse: LiveData<List<FaqList>> = _faqListResponse

    fun getFaqList(section: String){
        viewModelScope.launch {
            val response = faqListRepository.getFaqList(section)
            _faqListResponse.value = response.body()?.responseData?.faq

        }
    }
}