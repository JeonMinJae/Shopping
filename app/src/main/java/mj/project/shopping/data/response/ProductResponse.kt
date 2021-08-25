package mj.project.shopping.data.response

import mj.project.shopping.data.entitiy.product.ProductEntity
import java.util.*

data class ProductResponse(
    val id: String,
    val createdAt: Long,
    val productName: String,
    val productPrice: String,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
) {

    //엔티티로 바꿀수 있는 형태로 만든다.
    fun toEntity(): ProductEntity =
        ProductEntity(
            id = id.toLong(),
            createdAt = Date(createdAt),
            productName = productName,
            productPrice = productPrice.toDouble().toInt(),
            productImage = productImage,
            productType = productType,
            productIntroductionImage = productIntroductionImage
        )
}