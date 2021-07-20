package mj.project.shopping.data.network

import mj.project.shopping.data.response.ProductResponse
import mj.project.shopping.data.response.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// 내가 어떠한 요청을 어떻게 보낼건지 메서드를 정의
interface ProductApiService {

    // end point를 넣어주는것 ,필수파라미터가 있으면같이넣어준다.
    // <> 안에는  응답받을 Body 타입의 data class를 적어준다.
    @GET("products")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("products/{productId}")
    suspend fun getProduct(@Path("productId") productId: Long): Response<ProductResponse>
    //@Path는 사용자가 productId를 입력하면 다음 url에 응답한다.
}