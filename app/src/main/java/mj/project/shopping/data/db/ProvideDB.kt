package mj.project.shopping.data.db

import android.content.Context
import androidx.room.Room

internal fun provideDB(context: Context): ProductDatabase =
    Room.databaseBuilder(context, ProductDatabase::class.java, ProductDatabase.DB_NAME) //3번째인자는 db이름이다.
        .build()

internal fun provideToDoDao(database: ProductDatabase) = database.productDao()