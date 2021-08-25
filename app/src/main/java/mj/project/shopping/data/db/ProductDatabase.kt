package mj.project.shopping.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mj.project.shopping.data.db.dao.ProductDao
import mj.project.shopping.data.entitiy.product.ProductEntity
import mj.project.shopping.utillity.DateConverter

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false //스키마를 확인할 필요가 없으며 경고를 나타나지 않게 하기 위해서 사용
)

@TypeConverters(DateConverter::class)
abstract class ProductDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ProductDataBase.db"
    }

    abstract fun productDao(): ProductDao

}