package com.example.myapplication.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.News
import com.example.myapplication.model.ShopOwners
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*
import kotlinx.android.synthetic.main.shopowner_list.view.*

class ShopOwnerListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(news: ShopOwners?) {
        if (news != null) {
            itemView.tv_shopowner_name.text = news.shopName
            itemView.tv_address.text = news.shopAddress

        }
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.shopowner_list, parent, false)
            return NewsViewHolder(view)
        }
    }
}