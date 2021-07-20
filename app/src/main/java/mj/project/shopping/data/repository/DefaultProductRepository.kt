package mj.project.shopping.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mj.project.shopping.data.db.dao.ProductDao
import mj.project.shopping.data.entitiy.product.ProductEntity
import mj.project.shopping.data.network.ProductApiService

class DefaultProductRepository(
    private val productApi: ProductApiService,
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher
): ProductRepository {

    override suspend fun getProductList(): List<ProductEntity> = withContext(ioDispatcher) {
        val response = productApi.getProducts()
        return@withContext if (response.isSuccessful) {
            //map은 for문과 같은 반복문을 사용하지 않아도 지정한 함수로 인자를 여러번 전달해 그 결과를 list 형태로 뽑아 주는 일을한다.
            response.body()?.items?.map { it.toEntity() } ?: listOf() //body에 있는 아이템들을 꺼내서 엔티티로 바꾸고 널값이면 listof해라
        } else {
            listOf()
        }
    }

    override suspend fun getLocalProductList(): List<ProductEntity> = withContext(ioDispatcher) {
        productDao.getAll()
    }

    override suspend fun insertProductItem(ProductItem: ProductEntity): Long = withContext(ioDispatcher) {
        productDao.insert(ProductItem)
    }

    override suspend fun insertProductList(ProductList: List<ProductEntity>) {}

    override suspend fun updateProductItem(ProductItem: ProductEntity) {}

    override suspend fun getProductItem(itemId: Long): ProductEntity? = withContext(ioDispatcher) {
        val response = productApi.getProduct(itemId)
        return@withContext if (response.isSuccessful) {
            response.body()?.toEntity()
        } else {
            null
        }
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        productDao.deleteAll()
    }

    override suspend fun deleteProductItem(id: Long) {}


}