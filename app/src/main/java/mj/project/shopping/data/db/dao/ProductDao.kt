package mj.project.shopping.data.db.dao

import androidx.room.*
import mj.project.shopping.data.entitiy.product.ProductEntity

//Dao : Data Access Object의 약어로서 실질적으로 DB에 접근하는 객체
// room 사용
@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductEntity")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE id=:id")
    suspend fun getById(id: Long): ProductEntity?

    //onConflict = OnConflictStrategy.REPLACE는
    // Insert 할때 PrimaryKey가 겹치는 것이 있으면 덮어 쓴다는 의미이다.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ProductEntity: ProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ProductEntityList: List<ProductEntity>)

    @Query("DELETE FROM ProductEntity WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM ProductEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(ProductEntity: ProductEntity)

}