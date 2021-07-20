package mj.project.shopping.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.shopping.data.entitiy.product.ProductEntity
import mj.project.shopping.domain.product.GetProductItemUseCase
import mj.project.shopping.domain.product.OrderProductItemUseCase
import mj.project.shopping.presentation.BaseViewModel


internal class ProductDetailViewModel(
    private val productId: Long,
    private val getProductItemUseCase: GetProductItemUseCase,
    private val orderProductItemUseCase: OrderProductItemUseCase
): BaseViewModel() {

    // MutableData 변수명 앞에는 _를 쓴다. / LiveData는 그냥그대로 사용
    private var _productDetailState = MutableLiveData<ProductDetailState>(ProductDetailState.UnInitialized)
    val productDetailState: LiveData<ProductDetailState> = _productDetailState

    private lateinit var productEntity: ProductEntity

    override fun fetchData(): Job = viewModelScope.launch {
        setState(ProductDetailState.Loading)
        getProductItemUseCase(productId)?.let { product ->
            productEntity = product
            setState(
                ProductDetailState.Success(product)
            )
        } ?: kotlin.run {
            setState(ProductDetailState.Error)
        }
    }

    fun orderProduct() = viewModelScope.launch {
        val productId = orderProductItemUseCase(productEntity)
        if (productEntity.id == productId) {
            setState(ProductDetailState.Order)
        }
    }

    private fun setState(state: ProductDetailState) {
        _productDetailState.postValue(state)
    }

}