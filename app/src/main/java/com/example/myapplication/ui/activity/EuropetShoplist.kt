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
import com.example.myapplication.ui.adapter.ShopOwnerListAdapter
import com.example.myapplication.utill.State
import com.example.myapplication.viewmodel.NewsListViewModel
import com.example.myapplication.viewmodel.ShopOwnerListViewModel
import kotlinx.android.synthetic.main.activity_europet_shoplist.*
import kotlinx.android.synthetic.main.activity_news_list.*

class EuropetShoplist : AppCompatActivity() {

    private lateinit var viewModel: ShopOwnerListViewModel
    private lateinit var newsListAdapter: ShopOwnerListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_europet_shoplist)

        viewModel = ViewModelProviders.of(this)
            .get(ShopOwnerListViewModel::class.java)
        initAdapter()
        initState()
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progressbar_shop.visibility = if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            tv_error.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                newsListAdapter.setState(state ?: State.DONE)
            }
        })
    }

    private fun initAdapter() {
        newsListAdapter = ShopOwnerListAdapter { viewModel.retry() }
        rv_shoplist.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_shoplist.adapter = newsListAdapter
        viewModel.shopownerlist.observe(this, Observer {
            newsListAdapter.submitList(it)
        })
    }
}