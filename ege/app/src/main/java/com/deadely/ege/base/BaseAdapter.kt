package com.deadely.ege.base

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class CustomAdapter<T : Parcelable>(
    @LayoutRes private val itemRes: Int,
    private val onBind: (RecyclerView.ViewHolder, T) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: MutableList<T> = mutableListOf()
        private set

    fun setData(list: List<T>) {
        this.list.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun removeItem(pos: Int): Boolean {
        if (list.isEmpty())
            return false
        list.removeAt(pos.rem(list.size))
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos, list.size)
        notifyDataSetChanged()
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(itemRes, parent, false)
        ) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        onBind(holder, list[position.rem(list.size)])

    override fun getItemCount(): Int = list.size
}
