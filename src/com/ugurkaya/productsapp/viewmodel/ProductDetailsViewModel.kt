package com.ugurkaya.productsapp.viewmodel

import ProductDetailResponse
import ProductDetailResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ugurkaya.productsapp.network.RetrofitGetClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsViewModel() : ViewModel() {
    var productDetail: MutableLiveData<ProductDetailResult> = MutableLiveData<ProductDetailResult>()
    var productDetailResponse: ProductDetailResponse? = null

    init {
    }

    fun getProductDetail(code: String) {
        RetrofitGetClient().getApi().getProductDetail(
            code
        )?.enqueue(object : Callback<ProductDetailResponse?> {
            override fun onResponse(
                call: Call<ProductDetailResponse?>,
                response: Response<ProductDetailResponse?>
            ) {
                if (response.isSuccessful) {


                    response.body()?.let { resultResponse ->


                        if (productDetailResponse == null) {

                            productDetailResponse = resultResponse


                            productDetail.value = resultResponse.result


                        } else {
                            productDetailResponse = resultResponse
                            val newProductDetailResponse = resultResponse.result

                            productDetail.value = newProductDetailResponse

                        }


                    }


                }else{

                }
            }

            override fun onFailure(call: Call<ProductDetailResponse?>, t: Throwable) {

            }

        })
    }


    fun observeProductDetail(): LiveData<ProductDetailResult> {
        return productDetail
    }
}