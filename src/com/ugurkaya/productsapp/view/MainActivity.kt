package com.ugurkaya.productsapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ugurkaya.productsapp.adapter.HorizontalProductsPagerAdapter
import com.ugurkaya.productsapp.adapter.ProductsAdapter
import com.ugurkaya.productsapp.databinding.ActivityMainBinding
import com.ugurkaya.productsapp.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var horizontalProductsPagerAdapter: HorizontalProductsPagerAdapter

    var isScrolling = false


    lateinit var productViewModel: ProductsViewModel
    lateinit var productsAdapter: ProductsAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        initRecycler()

        productViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]





        productViewModel.observeProducts().observe(this) {

            productViewModel.products.value?.let { it1 ->

                it?.let { productResponse ->


                    productsAdapter.differ.submitList(productResponse.toList())

                }
            }
        }

        productViewModel.observeHorizontalProducts().observe(this) {

            productViewModel.horizontalProducts.value?.let { it1 ->

                it?.let { productResponse ->

                    horizontalProductsPagerAdapter = HorizontalProductsPagerAdapter(this)
                    horizontalProductsPagerAdapter.horizontalProducts = productResponse

                    view_pager.apply {
                        adapter = horizontalProductsPagerAdapter
                        binding.dotsIndicator.setViewPager(view_pager)
                    }
                }
            }
        }

        productsAdapter.setOnItemClickListener {
            val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
            intent.putExtra("code", it.code)
            startActivity(intent)

        }


        productViewModel.getProducts(
            productViewModel.nextURLVertical,
            binding.relativeMainAct,
            this
        )






        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })


    }


    val scrollListener = object : RecyclerView.OnScrollListener() {


        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val scrollable = recyclerView.canScrollVertically(1)
            val shouldPaginate =
                isAtLastItem && isNotAtBeginning && isScrolling && !scrollable
            if (shouldPaginate) {

                if (!productViewModel.didFinish) {
                    productViewModel.getProducts(
                        productViewModel.nextURLVertical,
                        binding.relativeMainAct,
                        this@MainActivity
                    )
                    isScrolling = false
                }



            } else {


                recyclerView.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }


        }


    }

    private fun initRecycler() {
        productsAdapter = ProductsAdapter()
        recyclerProducts.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            addOnScrollListener(this@MainActivity.scrollListener)
        }
    }


}


