package com.ugurkaya.productsapp.network

import ProductDetailResponse
import ProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {
    /**
     * retrieve products by given path
     */
    @GET("{path}")
    fun getProductsList(@Path("path") path: String?): Call<ProductsResponse?>?

    /**
     * retrieve product detail by given product code
     */
    @GET("1a1fb542-22d1-4919-914a-750114879775")
    fun getProductDetail(@Query("code") code:String?): Call<ProductDetailResponse>?
}