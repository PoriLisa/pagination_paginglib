package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.ShopOwners
import kotlinx.android.synthetic.main.shopowner_list.view.*

class ShopOwnerAdapter :
    PagedListAdapter<ShopOwners, ShopOwnerAdapter.UserViewHolder>(USER_COMPARATOR) {
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // private val imageView = view.user_avatar
        private val userName = view.tv_shopowner_name
        private val userEmail = view.tv_address
        fun bind(user: ShopOwners) {
            userName.text = user.shopName
            userEmail.text = user.shopAddress

        }
    }

    override fun onBindViewHolder(holder: ShopOwnerAdapter.UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopOwnerAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopowner_list, parent, false)
        return UserViewHolder(view)
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<ShopOwners>() {
            override fun areItemsTheSame(oldItem: ShopOwners, newItem: ShopOwners): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ShopOwners, newItem: ShopOwners): Boolean =
                newItem == oldItem
        }
    }
}