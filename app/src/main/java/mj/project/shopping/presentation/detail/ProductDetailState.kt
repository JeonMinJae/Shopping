package mj.project.shopping.presentation.detail

import mj.project.shopping.data.entitiy.product.ProductEntity


sealed class ProductDetailState {

    object UnInitialized: ProductDetailState()

    object Loading: ProductDetailState()

    data class Success(
        val productEntity: ProductEntity
    ): ProductDetailState()

    object Order: ProductDetailState()

    object Error: ProductDetailState()

}