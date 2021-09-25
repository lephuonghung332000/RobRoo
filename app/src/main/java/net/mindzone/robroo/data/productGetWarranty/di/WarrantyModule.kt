package net.mindzone.robroo.data.productGetWarranty.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.productGetWarranty.remote.RemoteWarrantyDataSource
import net.mindzone.robroo.data.productGetWarranty.remote.RemoteWarrantyDataSourceImp
import net.mindzone.robroo.data.productGetWarranty.repository.WarrantyRepository
import net.mindzone.robroo.data.productGetWarranty.repository.WarrantyRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WarrantyModule {
    @Provides
    @Singleton
    fun provideWarrantyDataSource(apiClient: ApiClient): RemoteWarrantyDataSource =
        RemoteWarrantyDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideWarrantyRepository(remoteWarrantyDataSource: RemoteWarrantyDataSource): WarrantyRepository =
        WarrantyRepositoryImp(remoteWarrantyDataSource)
}