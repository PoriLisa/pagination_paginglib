package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.News
import com.example.myapplication.model.ShopOwners
import com.example.myapplication.utill.State
import com.example.myapplication.viewholder.ListFooterViewHolder
import com.example.myapplication.viewholder.NewsViewHolder
import com.example.myapplication.viewholder.ShopOwnerListViewHolder
import com.example.myapplication.viewmodel.ShopOwnerListViewModel

class ShopOwnerListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<ShopOwners, RecyclerView.ViewHolder>(ShopOwnerListAdapter.NewsDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as ShopOwnerListViewHolder).bind(getItem(position))
        else (holder as ListFooterViewHolder).bind(state)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) NewsViewHolder.create(parent) else ListFooterViewHolder.create(
            retry,
            parent
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<ShopOwners>() {
            override fun areItemsTheSame(oldItem: ShopOwners, newItem: ShopOwners): Boolean {
                return oldItem.shopName == newItem.shopName
            }

            override fun areContentsTheSame(oldItem: ShopOwners, newItem: ShopOwners): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}