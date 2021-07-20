package mj.project.shopping.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mj.project.shopping.data.db.dao.ProductDao
import mj.project.shopping.data.entitiy.product.ProductEntity
import mj.project.shopping.utillity.DateConverter

// Room은 스마트폰 내장 DB에 데이터를 저장하기 위해 사용하는 라이브러리
@Database(
    entities = [ProductEntity::class],
    version = 1, //version은 앱을 업데이트하다가 entity의 구조를 변경해야 하는 일이 생겼을 때 이전 구조와 현재 구조를 구분해주는 역할을 한다. 만약 구조가 바뀌었는데 버전이 같다면 에러가 뜨며 디버깅이 되지 않는다
    exportSchema = false //스키마를 확인할 필요가 없으며 경고를 나타나지 않게 하기 위해서 사용
)

@TypeConverters(DateConverter::class)
abstract class ProductDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ProductDataBase.db"
    }

    abstract fun productDao(): ProductDao

}