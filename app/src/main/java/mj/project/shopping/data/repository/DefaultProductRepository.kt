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
            response.body()?.items?.map { it.toEntity() } ?: listOf()
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