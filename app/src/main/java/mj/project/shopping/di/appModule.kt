package mj.project.shopping.di

import kotlinx.coroutines.Dispatchers
import mj.project.shopping.data.db.provideDB
import mj.project.shopping.data.db.provideToDoDao
import mj.project.shopping.data.network.*
import mj.project.shopping.data.network.provideProductRetrofit
import mj.project.shopping.data.preference.PreferenceManager
import mj.project.shopping.data.repository.DefaultProductRepository
import mj.project.shopping.data.repository.ProductRepository
import mj.project.shopping.domain.product.DeleteOrderedProductListUseCase
import mj.project.shopping.domain.product.GetOrderedProductListUseCase
import mj.project.shopping.domain.product.GetProductItemUseCase
import mj.project.shopping.domain.product.GetProductListUseCase
import mj.project.shopping.domain.product.OrderProductItemUseCase
import mj.project.shopping.presentation.detail.ProductDetailViewModel
import mj.project.shopping.presentation.list.ProductListViewModel
import mj.project.shopping.presentation.main.MainViewModel
import mj.project.shopping.presentation.profile.ProfileViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    // ViewModel (type missmatch 시에 gradle.properties에서 android.enableJetifier=true를 추가시켜주자)
    viewModel { MainViewModel() }
    viewModel { ProductListViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { (productId: Long) -> ProductDetailViewModel(productId, get(), get()) }

    // UseCase
    // 요청할 때마다 매번 새로운 객체를 생성합니다.
    factory { GetProductListUseCase(get()) }
    factory { GetOrderedProductListUseCase(get()) }
    factory { GetProductItemUseCase(get()) }
    factory { OrderProductItemUseCase(get()) }
    factory { DeleteOrderedProductListUseCase(get()) }

    // Repository
    single<ProductRepository> { DefaultProductRepository(get(), get(), get()) }

    //ProvideAPI
    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideProductRetrofit(get(), get()) }
    single { provideProductApiService(get()) }

    //PreferenceManager
    single { PreferenceManager(androidContext()) }

    // Database
    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }

    single { Dispatchers.Main }
    single { Dispatchers.IO }
}
