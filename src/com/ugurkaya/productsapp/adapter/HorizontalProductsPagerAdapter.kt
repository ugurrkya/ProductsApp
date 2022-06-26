package com.ugurkaya.productsapp.adapter

import HorizontalProducts
import Products
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.MutableLiveData

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide

import com.ugurkaya.productsapp.R
import com.ugurkaya.productsapp.view.ProductDetailActivity
import kotlinx.android.synthetic.main.horizontal_product_items.view.*

import kotlinx.android.synthetic.main.product_items.view.*
import kotlinx.android.synthetic.main.product_items.view.productName


class HorizontalProductsPagerAdapter(val context: Context) : PagerAdapter() {
    lateinit var horizontalProducts: List<HorizontalProducts>
    var layoutInflater: LayoutInflater? = null
    override fun getCount(): Int {
        return horizontalProducts.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.horizontal_product_items, container, false)

        view.productName.text = horizontalProducts.get(position).name

        var productFormatted = String.format("%.2f", horizontalProducts.get(position).price);
        view.horizontalProductPrice.text =
            String.format(context.resources.getString(R.string.productPriceText), productFormatted)
        view.horizontalFollowCount.text = String.format(
            context.resources.getString(R.string.followCountText),
            horizontalProducts.get(position).followCount
        )


        if (horizontalProducts.get(position).countOfPrices != null) {
            view.horizontalCountOfPrices.visibility = View.VISIBLE
            view.icon_arrow_h.visibility = View.VISIBLE
            view.horizontalCountOfPrices.text = String.format(
                context.resources.getString(R.string.countOfPricesText),
                horizontalProducts.get(position).countOfPrices
            )

        } else {
            view.horizontalCountOfPrices.visibility = View.INVISIBLE
            view.icon_arrow_h.visibility = View.INVISIBLE
        }

        if (horizontalProducts.get(position).dropRatio != null) {
            view.discountRate.visibility = View.VISIBLE



            view.discountTextHR.text = String.format(
                context.resources.getString(R.string.dropRatioText), Math.round(
                    horizontalProducts.get(position).dropRatio!!
                ).toInt().toString()
            )


        } else {
            view.discountRate.visibility = View.INVISIBLE

        }

        Glide.with(context).load(horizontalProducts.get(position).imageUrl)
            .into(view.horizontalProductImage)


        view.setOnClickListener {

            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("code", horizontalProducts.get(position).code)
            context.startActivity(intent)
        }

        container.addView(view)
        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }


}