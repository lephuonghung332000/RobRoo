package net.mindzone.robroo.ui.main.generalMenu.applicationManual.applicationManualDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.faqArticle.entity.FaqArticle
import net.mindzone.robroo.data.faqArticle.repository.FaqArticleRepository
import net.mindzone.robroo.utils.extensions.Status
import javax.inject.Inject

@HiltViewModel
class HomeSideMenuApplicationManualDetailViewModel @Inject constructor(private val faqArticleRepository: FaqArticleRepository) :
    BaseFragmentVM() {
    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status
    private var _faqArticleResponse = MutableLiveData<FaqArticle>()
    val faqListResponse: LiveData<FaqArticle> = _faqArticleResponse

    fun getFaqArticle(faq_id: String){
        _status.value = Status.LOADING
        viewModelScope.launch {

            try {
                val response = faqArticleRepository.getFaqArticle(faq_id)
                _faqArticleResponse.value = response.body()?.responseData?.faqarticle
                _status.value = Status.SUCCESS
            }catch (e : Exception){
                _status.value = Status.ERROR
            }
        }
    }
}