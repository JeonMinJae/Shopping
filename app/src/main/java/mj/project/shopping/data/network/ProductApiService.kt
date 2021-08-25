package mj.project.shopping.data.network

import mj.project.shopping.data.response.ProductResponse
import mj.project.shopping.data.response.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("products/{productId}")
    suspend fun getProduct(@Path("productId") productId: Long): Response<ProductResponse>
    //@Path는 @pathparameter의 줄임으로 URL 경로에 변수를 넣어주는것을 의미한다.
    //@Path어노테이션을 이용해서 {템플릿 변수} 와 동일한 이름을 갖는 파라미터를 추가한다.
}