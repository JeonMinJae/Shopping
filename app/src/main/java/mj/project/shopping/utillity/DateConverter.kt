package mj.project.shopping.utillity

import androidx.room.TypeConverter
import java.util.*

// JSON과 같은 데이터는 자바나 코틀린에서 바로 사용할 수 있는 데이터 형식이 아니기 때문에
//이를 변환해주기 위해 이러한 converter를 사용해야 한다.
object DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

}