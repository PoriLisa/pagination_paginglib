package com.example.myapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.adapter.NewsListAdapter
import com.example.myapplication.ui.adapter.ShopOwnerAdapter
import com.example.myapplication.ui.adapter.ShopOwnerListAdapter
import com.example.myapplication.utill.State
import com.example.myapplication.viewmodel.NewsListViewModel
import com.example.myapplication.viewmodel.ShopOwnerListViewModel
import com.example.myapplication.viewmodel.ShopOwnerViewModel
import kotlinx.android.synthetic.main.activity_europet_shoplist.*
import kotlinx.android.synthetic.main.activity_news_list.*

class EuropetShoplist : AppCompatActivity() {

    private lateinit var viewModel: ShopOwnerViewModel
    private lateinit var newsListAdapter: ShopOwnerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_europet_shoplist)

        viewModel = ViewModelProviders.of(this)
            .get(ShopOwnerViewModel::class.java)
        initAdapter()

    }




    private fun initAdapter() {
        newsListAdapter = ShopOwnerAdapter()
        rv_shoplist.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)



        viewModel.userPagedList.observe(this, Observer {

            newsListAdapter.submitList(it)
        })
        rv_shoplist.adapter = newsListAdapter
    }
}