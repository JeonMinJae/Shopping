package mj.project.shopping.presentation.list

import mj.project.shopping.data.entitiy.product.ProductEntity

//상태불변성 표현
internal sealed class ProductListState {

    object UnInitialized: ProductListState()

    object Loading: ProductListState()

    data class Success(
        val productList: List<ProductEntity>
    ): ProductListState()

    object Error: ProductListState()

}