package mj.project.shopping.data.response

//아이템과 카운터를 받는용도
data class ProductsResponse(
    val items: List<ProductResponse>,
    val count: Int
)