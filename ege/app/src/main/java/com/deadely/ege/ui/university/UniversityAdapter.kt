package com.deadely.ege.ui.university

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deadely.ege.R
import com.deadely.ege.model.University
import kotlinx.android.synthetic.main.row_university.view.*

class UniversityAdapter(
    private var list: MutableList<University>,
    val clickListener: OnClickListener? = null
) : RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UniversityAdapter.UniversityViewHolder = UniversityViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_university, parent, false)
    )

    override fun onBindViewHolder(holder: UniversityAdapter.UniversityViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class UniversityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(unit: University) {
            with(itemView) {
                title.text = unit.name
                Glide.with(itemView.context)
                    .load(unit.image)
                    .error(R.drawable.ic_error)
                    .into(image)
                itemView.setOnClickListener { clickListener?.onClick(unit) }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun setData(newList: List<University>) {
        list = newList.toMutableList()
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(unit: University)
    }
}