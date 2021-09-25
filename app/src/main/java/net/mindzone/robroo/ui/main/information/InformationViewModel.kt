package net.mindzone.robroo.ui.main.information

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.manualDocument.entity.documentSearch.DocumentSearchResponse
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import net.mindzone.robroo.data.manualDocument.entity.listDocument.ListDocumentResponse
import net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ListDocumentTypeResponse
import net.mindzone.robroo.data.manualDocument.entity.listLanguage.ListLanguageResponse
import net.mindzone.robroo.data.manualDocument.repository.documentSearch.DocumentSearchRepository
import net.mindzone.robroo.data.manualDocument.repository.downloadDoc.DownloadDocRepository
import net.mindzone.robroo.data.manualDocument.repository.listDocument.ListDocumentRepository
import net.mindzone.robroo.data.manualDocument.repository.listDocumentType.ListDocumentTypeRepository
import net.mindzone.robroo.data.manualDocument.repository.listLanguage.ListLanguageRepository
import net.mindzone.robroo.data.manualDocument.repository.local.ItemRepository
import net.mindzone.robroo.data.productGetWarranty.entity.WarrantyResponse
import net.mindzone.robroo.data.productGetWarranty.repository.WarrantyRepository
import net.mindzone.robroo.data.productInfo.entity.InfoResponse
import net.mindzone.robroo.data.productInfo.repository.InfoRepository
import net.mindzone.robroo.data.productModel.entity.FieldsReponse
import net.mindzone.robroo.data.productModel.repository.FieldsRepository
import net.mindzone.robroo.data.productgroupList.entity.SubGroupsResponse
import net.mindzone.robroo.data.productgroupList.repository.SubGroupsRepository
import net.mindzone.robroo.data.productgroupListproductmodel.entity.ListProductModelResponse
import net.mindzone.robroo.data.productgroupListproductmodel.repository.ListProductModelRepository
import net.mindzone.robroo.data.productmodelListimplement.entity.ListImplementResponse
import net.mindzone.robroo.data.productmodelListimplement.repository.ListImplementRepository
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.utils.extensions.Status
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(

    private val fieldsRepository: FieldsRepository,
    val sharedPreferencesHelper: SharedPreferencesHelper,
    private val subGroupsRepository: SubGroupsRepository,
    private val listProductModelRepository: ListProductModelRepository,
    private val infoRepository: InfoRepository,
    private val listImplementRepository: ListImplementRepository,
    private val warrantyRepository: WarrantyRepository,
    private val listLanguageRepository: ListLanguageRepository,
    private val listDocumentTypeRepository: ListDocumentTypeRepository,
    private val listDocumentRepository: ListDocumentRepository,
    private val documentSearchRepository: DocumentSearchRepository,
    private val downloadDocRepository: DownloadDocRepository,
    private val itemRepository: ItemRepository
) : BaseFragmentVM() {
    var azureId : String = ""
    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
        Log.d("asdfadsfa", azureId)
    }
    private var _fieldsResponse = MutableLiveData<Response<FieldsReponse>>()
    val fieldsResponse: LiveData<Response<FieldsReponse>> = _fieldsResponse
    fun getFields(azure_id: String,model_id:String) {
        Log.e("here", "===========")
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _fieldsResponse.value = fieldsRepository.getFields(azure_id,model_id)
                Log.e("herse", "===========")
                _status.value = Status.SUCCESS
            } catch (e: Exception) {
                _status.value = Status.ERROR
            }
        }
    }
    private var _warrantyResponse = MutableLiveData<Response<WarrantyResponse>>()
    val warrantyResponse: LiveData<Response<WarrantyResponse>> = _warrantyResponse
    fun getWarrantyInfo(azure_id: String,model_id:String) {
        Log.e("here", "===========")
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _warrantyResponse.value = warrantyRepository.getWarranty(azure_id,model_id)
                Log.e("herse", "===========")
                _status.value = Status.SUCCESS
            } catch (e: Exception) {
                _status.value = Status.ERROR
            }
        }
    }

    private var _infoResponse = MutableLiveData<Response<InfoResponse>>()
    val infoResponse: LiveData<Response<InfoResponse>> = _infoResponse
    fun getInfo(azure_id: String,model_id:String) {
        Log.e("here", "===========")
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _infoResponse.value = infoRepository.getInfo(azure_id,model_id)
                Log.e("herse", "===========")
                _status.value = Status.SUCCESS
            } catch (e: Exception) {
                _status.value = Status.ERROR
            }
        }
    }

    private var _subGroupsResponse = MutableLiveData<Response<SubGroupsResponse>>()
    val subGroupsResponse: LiveData<Response<SubGroupsResponse>> = _subGroupsResponse
    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
    }

    fun getSubGroups(azure_id: String) {
        _status.value = Status.LOADING
        viewModelScope.launch {
          try {
              _subGroupsResponse.value = subGroupsRepository.getSubGroups(azure_id)
              Log.e("kaka", _subGroupsResponse.value.toString())
              _status.value = Status.SUCCESS
          }  catch (e:Exception){
              _status.value = Status.ERROR
          }

        }
    }

    private var _listProductResponse = MutableLiveData<Response<ListProductModelResponse>>()
    val listProductModelResponse: LiveData<Response<ListProductModelResponse>> = _listProductResponse
    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
    }

    fun getListProductModel(azure_id: String,group_id:Int) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _listProductResponse.value = listProductModelRepository.getListProductModel(azure_id,group_id)
                _status.value = Status.SUCCESS
            }  catch (e:Exception){
                _status.value = Status.ERROR
            }
        }
    }
    private var _listImplementResponse = MutableLiveData<Response<ListImplementResponse>>()
    val listImplementResponse: LiveData<Response<ListImplementResponse>> = _listImplementResponse
    fun getListImplement(azure_id: String,model_id: String) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _listImplementResponse.value = listImplementRepository.getListImplement(azure_id,model_id)
                _status.value = Status.SUCCESS
            }catch (e:Exception){
                _status.value = Status.ERROR
            }
        }
    }

    private var _listLanguageResponse = MutableLiveData<Response<ListLanguageResponse>>()
    val listLanguageResponse: LiveData<Response<ListLanguageResponse>> = _listLanguageResponse
    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
    }

    fun getListLanguage(azure_id: String) {
        viewModelScope.launch {
            try {
                _listLanguageResponse.value = listLanguageRepository.getListLanguage(azure_id)
            }  catch (e:Exception){
            }
        }
    }
    private var _listDocumentResponse = MutableLiveData<Response<ListDocumentResponse>>()
    val listDocumentResponse: LiveData<Response<ListDocumentResponse>> = _listDocumentResponse
    private var _listDocumentItems = MutableLiveData<List<Items>>()
    var listDocumentItems: LiveData<List<Items>> = _listDocumentItems
    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
    }

    var i=0
    fun getListDocument(azure_id: String, start:Int, size:Int, model_id:String, sort:String,doclangid:Int?, doctypeid:Int?) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                _listDocumentResponse.value = listDocumentRepository.getListDocument(
                    azure_id,
                    start,
                    size,
                    model_id,
                    sort,
                    doclangid,
                    doctypeid
                )

                _listDocumentItems.value = listDocumentResponse.value?.body()?.responseData?.items
                _status.value = Status.SUCCESS
            }
            catch (e:Exception){
                e.printStackTrace()
                _status.value = Status.ERROR
            }
        }
    }

    private var _listDocumentTypeResponse = MutableLiveData<Response<ListDocumentTypeResponse>>()
    val listDocumentTypeResponse: LiveData<Response<ListDocumentTypeResponse>> = _listDocumentTypeResponse
    init {
        azureId = sharedPreferencesHelper.getCurrentUserId()
    }
    fun getListDocumentType(azure_id: String) {
        viewModelScope.launch {
            try {
                _listDocumentTypeResponse.value = listDocumentTypeRepository.getListDocumentType(azure_id)
            }  catch (e:Exception){
            }
        }
    }




    fun updateData(items: Items){
        viewModelScope.launch {
            itemRepository.update(items)
        }
    }

    fun insertData(items: Items){
        viewModelScope.launch {
            itemRepository.insert(items)
        }
    }

    fun deleteData(docnumber:String){
        viewModelScope.launch {
            itemRepository.delete(docnumber)
        }
    }
    fun getAllPdf(): LiveData<List<Items>> {
        return itemRepository.getAllItems()
    }


}