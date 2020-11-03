package com.deadely.ege.ui.discipline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deadely.ege.R
import com.deadely.ege.model.Variant
import kotlinx.android.synthetic.main.row_variants.view.*

class VariantsAdapter(
    private var list: List<Variant>,
    val clickListener: OnClickListener? = null
) : RecyclerView.Adapter<VariantsAdapter.VariantsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VariantsAdapter.VariantsViewHolder = VariantsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_variants, parent, false)
    )

    override fun onBindViewHolder(holder: VariantsAdapter.VariantsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class VariantsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(unit: Variant) {
            with(itemView) {
                variantName.text = unit.title
                itemView.setOnClickListener { clickListener?.onClick(unit) }
                divider.visibility = if (unit == list.last()) View.GONE else View.VISIBLE
            }
        }

    }

    override fun getItemCount(): Int = list.size

    fun setData(newList: List<Variant>) {
        list = newList.toMutableList()
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(unit: Variant)
    }
}