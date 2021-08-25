package mj.project.shopping.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import mj.project.shopping.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal fun provideProductApiService(retrofit: Retrofit): ProductApiService {
    return retrofit.create(ProductApiService::class.java)
}

internal fun provideProductRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Url.PRODUCT_BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
}

internal fun provideGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create(
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) //모든 단어를 소문자로 표기하고, 단어와 단어는 _ 를 삽입하여 구분한다.
            .create()
    )
}

internal fun buildOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY // Logs request and response lines and their respective headers and bodies (if present).
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE  // No logs.
    }
    return OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}