package com.ugurkaya.productsapp.viewmodel

import HorizontalProducts
import Products
import ProductsResponse
import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ugurkaya.productsapp.network.RetrofitGetClient
import com.ugurkaya.productsapp.view.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductsViewModel() : ViewModel() {
    var products: MutableLiveData<List<Products>> = MutableLiveData<List<Products>>()
    var horizontalProducts: MutableLiveData<List<HorizontalProducts>> =
        MutableLiveData<List<HorizontalProducts>>()
    var nextURLVertical: String = "59906f35-d5d5-40f7-8d44-53fd26eb3a05"


    @SuppressLint("StaticFieldLeak")
    var progressBar: ProgressBar? = null

    var didFinish = false

    var productsResponse: ProductsResponse? = null
    var horizontalProductsResponse: ProductsResponse? = null


    init {


    }

    fun getProducts(nextUrl: String, relativeLayout: RelativeLayout, activity: Activity) {


         if (!didFinish) {

        addRelativeProgress(activity, relativeLayout, true, 1)



        RetrofitGetClient().getApi().getProductsList(
            nextUrl
        )?.enqueue(object : Callback<ProductsResponse?> {
            override fun onResponse(
                call: Call<ProductsResponse?>,
                response: Response<ProductsResponse?>
            ) {
                if (response.isSuccessful) {


                    response.body()?.let { resultResponse ->
                        addRelativeProgress(activity, relativeLayout, false, 1)

                        if (productsResponse == null && horizontalProductsResponse == null) {

                            horizontalProductsResponse = resultResponse
                            productsResponse = resultResponse

                            products.value = resultResponse.result.products.toMutableList()
                            horizontalProducts.value =
                                resultResponse.result.horizontalProducts.toMutableList()
                            nextURLVertical = response.body()?.result?.nextUrl?.replace(
                                RetrofitGetClient().BASE_URL,
                                ""
                            )
                                .toString()


                        } else {


                            if (productsResponse != null) {
                                val currentProducts =
                                    productsResponse?.result?.products?.toMutableList()
                                val newProducts = resultResponse.result.products.toMutableList()

                                currentProducts?.addAll(newProducts)
                                nextURLVertical = response.body()?.result?.nextUrl?.replace(
                                    RetrofitGetClient().BASE_URL,
                                    ""
                                )
                                    .toString()

                                if (response.body()?.result?.nextUrl == null) {

                                    didFinish = true
                                }

                                products.value = currentProducts
                            }


                        }


                    }


                } else {
                    didFinish = true
                    addRelativeProgress(activity, relativeLayout, false, 1)

                }


            }

            override fun onFailure(call: Call<ProductsResponse?>, t: Throwable) {
                didFinish = true
                addRelativeProgress(activity, relativeLayout, false, 1)


            }

        })
    }
       // }
    }

    fun observeProducts(): LiveData<List<Products>> {
        return products
    }

    fun observeHorizontalProducts(): LiveData<List<HorizontalProducts>> {
        return horizontalProducts
    }

    fun addRelativeProgress(ac: Activity?, layout: RelativeLayout, show: Boolean, color: Int) {
        if (show) {
            progressBar = ProgressBar(ac, null, R.attr.progressBarStyleLarge)
            if (color == 0) {
                progressBar!!.indeterminateDrawable
                    .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.MULTIPLY)
            } else if (color == 1) {
                progressBar!!.indeterminateDrawable
                    .setColorFilter(Color.parseColor("#9c1a57"), PorterDuff.Mode.MULTIPLY)
            }
            val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(250, 250)
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            layout.addView(progressBar, params)
            progressBar!!.setVisibility(View.VISIBLE)
        } else {
            if (progressBar != null) {
                try {
                    progressBar!!.setVisibility(View.GONE)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}