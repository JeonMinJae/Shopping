package mj.project.shopping.domain.product

import mj.project.shopping.data.repository.ProductRepository
import mj.project.shopping.domain.UseCase

internal class DeleteOrderedProductListUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke() {
        return productRepository.deleteAll()
    }

}