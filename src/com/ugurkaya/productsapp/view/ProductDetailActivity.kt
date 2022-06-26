package com.ugurkaya.productsapp.view


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ugurkaya.productsapp.R
import com.ugurkaya.productsapp.databinding.ActivityProductDetailBinding
import com.ugurkaya.productsapp.viewmodel.ProductDetailsViewModel

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    lateinit var productDetailViewModel: ProductDetailsViewModel
    private var code = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productDetailViewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]
        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getIntentDetails()




        productDetailViewModel.observeProductDetail().observe(this) {

            productDetailViewModel.productDetail.value?.let { it1 ->




                it?.let { productResponse ->


                    binding.apply {


                        binding.brandName.text = productResponse.mkName
                        binding.productName.text = productResponse.productName
                        binding.productBadge.text = productResponse.badge

                        if (productResponse.freeShipping == true) {
                            binding.freeShippingText.visibility = View.VISIBLE
                        } else {
                            binding.freeShippingText.visibility = View.INVISIBLE
                        }

                        var productFormatted =
                            productResponse.price?.let { it2 ->
                                String.format(
                                    "%.2f",
                                    it2.toDouble()
                                )
                            };
                        binding.priceText.text = String.format(
                            resources.getString(R.string.productPriceText),
                            productFormatted
                        )
                        binding.countOfPricesText.text = String.format(
                            resources.getString(R.string.countOfPricesDetailText),
                            productResponse.countOfPrices.toString()
                        )
                        binding.productRating.rating = productResponse.rating?.toFloat() ?: 0.0f
                        binding.lastUpdateText.text = String.format(
                            resources.getString(R.string.lastUpdateText),
                            productResponse.lastUpdate.toString()
                        )

                        binding.capacityOptionOne.text =
                            productResponse.storageOptions.get(0).toString()
                        binding.capacityOptionTwo.text =
                            productResponse.storageOptions.get(1).toString()
                        binding.capacityOptionThree.text =
                            productResponse.storageOptions.get(2).toString()
                        downloadImage(binding.productImage, productResponse.imageUrl)

                    }


                }
            }
        }

        try {
            productDetailViewModel.getProductDetail(code)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    companion object {
        @BindingAdapter("android:downloadImageView")
        @JvmStatic
        fun downloadImage(view: ImageView?, url: String?) {
            view?.let {
                Glide.with(it.context).load(url)
                    .into(view)
            }
        }
    }

    private fun getIntentDetails() {
        val myIntent = intent
        this.code = myIntent.getIntExtra("code", 0).toString()

    }
}