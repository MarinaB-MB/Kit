package com.deadely.ege.ui.resultlist

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deadely.ege.R
import com.deadely.ege.model.Asks
import com.deadely.ege.utils.Utils
import kotlinx.android.synthetic.main.row_ask.view.*

class AsksAdapter(
    private var list: List<Asks>,
) : RecyclerView.Adapter<AsksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AsksAdapter.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_ask, parent, false)
    )

    override fun onBindViewHolder(holder: AsksAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(unit: Asks) {
            with(itemView) {
                tvNumber.text = Utils.getString(R.string.ask_number).format(list.size)
                tvText.isVerticalScrollBarEnabled = false
                val value =
                    "<html><body dir=\\\"rtl\\\"; style=\\\"text-align:justify;\\\">${unit.ask}</body></html>"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvText.text = Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT);
                } else {
                    tvText.text = Html.fromHtml(value);
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun setData(newList: List<Asks>) {
        list = newList
        notifyDataSetChanged()
    }

}