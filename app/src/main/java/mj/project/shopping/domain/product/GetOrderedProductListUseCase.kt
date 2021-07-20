package mj.project.shopping.domain.product

import mj.project.shopping.data.entitiy.product.ProductEntity
import mj.project.shopping.data.repository.ProductRepository
import mj.project.shopping.domain.UseCase


internal class GetOrderedProductListUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke(): List<ProductEntity> {
        return productRepository.getLocalProductList()
    }

}