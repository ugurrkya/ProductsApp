package com.ugurkaya.productsapp.model

import HorizontalProducts
import Products
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class Result (
	@SerializedName("nextUrl")  @Expose val nextUrl : String,
	@SerializedName("horizontalProducts")  @Expose val horizontalProducts : MutableList<HorizontalProducts>,
	@SerializedName("products")  @Expose val products : MutableList<Products>
)