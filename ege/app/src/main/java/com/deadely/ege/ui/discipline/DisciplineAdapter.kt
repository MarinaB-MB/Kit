package com.deadely.ege.ui.discipline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deadely.ege.R
import com.deadely.ege.model.Disciplines
import com.deadely.ege.model.Variant
import com.deadely.ege.utils.CURRENT_DISCIPLINE
import com.deadely.ege.utils.PreferencesManager
import com.deadely.ege.utils.PreferencesManager.set
import kotlinx.android.synthetic.main.row_discipline.view.*

class DisciplineAdapter(
    private var list: MutableList<Disciplines>,
    val clickListener: OnClickListener? = null
) : RecyclerView.Adapter<DisciplineAdapter.DisciplinesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DisciplineAdapter.DisciplinesViewHolder = DisciplinesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_discipline, parent, false)
    )


    override fun onBindViewHolder(holder: DisciplineAdapter.DisciplinesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class DisciplinesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(unit: Disciplines) {
            val preferences = PreferencesManager.defaultPrefs(itemView.context)
            with(itemView) {
                expandableLayout.collapse()
                tvName.text = unit.name
                val variantAdapter =
                    VariantsAdapter(unit.variants.toMutableList().sortedBy { it.number },
                        object : VariantsAdapter.OnClickListener {
                            override fun onClick(unit: Variant) {
                                clickListener?.onVariantClick(unit)
                                preferences[CURRENT_DISCIPLINE] = unit.eid
                            }
                        })
                rvVariants.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = variantAdapter
                }

                itemView.setOnClickListener {
                    if (expandableLayout.isExpanded) expandableLayout.collapse()
                    else expandableLayout.expand()
                }
                divider.visibility = if (unit == list.last()) View.GONE else View.VISIBLE
            }
        }

    }

    override fun getItemCount(): Int = list.size

    fun setData(newList: List<Disciplines>) {
        list = newList.toMutableList()
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onVariantClick(unit: Variant)
    }
}