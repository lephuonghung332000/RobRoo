package net.mindzone.robroo.data.productGetWarranty.remote
import net.mindzone.robroo.data.productGetWarranty.entity.WarrantyResponse
import retrofit2.Response

interface RemoteWarrantyDataSource {
    suspend fun getWarrantyInfo(azureId:String,model_id:String) : Response<WarrantyResponse>
}