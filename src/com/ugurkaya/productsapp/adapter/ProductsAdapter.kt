package com.ugurkaya.productsapp.adapter

import Products
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ugurkaya.productsapp.R
import kotlinx.android.synthetic.main.product_items.view.*
import java.text.NumberFormat


class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {


    inner class ProductsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_items,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Products) -> Unit)? = null

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.itemView.apply {

            productName.text = product.name



            Glide.with(context).load(product.imageUrl)
                .into(productImage)




            if(product.countOfPrices != null){
                countOfPrices.visibility = View.VISIBLE
                icon_arrow.visibility = View.VISIBLE
                countOfPrices.text = String.format(resources.getString(R.string.countOfPricesText), product.countOfPrices)

            }else{
                countOfPrices.visibility = View.INVISIBLE
                icon_arrow.visibility = View.INVISIBLE
            }
            if (product.dropRatio != null){
                discount_rate.visibility = View.VISIBLE



                discountText.text = String.format(resources.getString(R.string.dropRatioText), Math.round(product.dropRatio).toInt().toString())


            }else{
                discount_rate.visibility = View.INVISIBLE

            }

            var productFormatted = String.format("%.2f", product.price);
            productPrice.text = String.format(resources.getString(R.string.productPriceText), productFormatted)
            followCount.text = String.format(resources.getString(R.string.followCountText), product.followCount)

            setOnClickListener {
                onItemClickListener?.let { it(product) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Products) -> Unit) {
        onItemClickListener = listener
    }

}