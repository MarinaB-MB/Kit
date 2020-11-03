package com.deadely.ege.ui.resultlist

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deadely.ege.R
import com.deadely.ege.model.Asks
import com.deadely.ege.utils.Utils
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.row_ask.view.*

class AsksAdapter(
    private var list: List<Asks>,
    private val activity: Activity
) : RecyclerView.Adapter<AsksAdapter.AsksViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AsksAdapter.AsksViewHolder = AsksViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_ask, parent, false)
    )

    override fun onBindViewHolder(holder: AsksAdapter.AsksViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class AsksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(unit: Asks) {
            with(itemView) {
                tvNumber.text = Utils.getString(R.string.ask_number).format(list[position].number)
                tvText.isVerticalScrollBarEnabled = false
                val value =
                    "<html><body dir=\\\"rtl\\\"; style=\\\"text-align:justify;\\\">${unit.ask}</body></html>"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvText.text = Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT);
                } else {
                    tvText.text = Html.fromHtml(value);
                }
                if (unit.answer[0].right) one.setTextColor(Utils.getColor(R.color.green)) else one.setTextColor(
                    Utils.getColor(R.color.red)
                )
                if (unit.answer[1].right) two.setTextColor(Utils.getColor(R.color.green)) else two.setTextColor(
                    Utils.getColor(R.color.red)
                )
                if (unit.answer[2].right) three.setTextColor(Utils.getColor(R.color.green)) else three.setTextColor(
                    Utils.getColor(R.color.red)
                )
                if (unit.answer[3].right) four.setTextColor(Utils.getColor(R.color.green)) else four.setTextColor(
                    Utils.getColor(R.color.red)
                )
                one.text = unit.answer[0].answer
                two.text = unit.answer[1].answer
                three.text = unit.answer[2].answer
                four.text = unit.answer[3].answer
                if (unit.image.isNotEmpty()) {
                    image.makeVisible()
                    if (unit.isSvg) {
                        GlideToVectorYou.justLoadImage(activity, Uri.parse(unit.image), image)
                    } else {
                        Glide.with(image.context)
                            .load(unit.image)
                            .placeholder(R.drawable.ic_image_lost)
                            .error(R.drawable.ic_error)
                            .into(image)
                    }
                } else image.makeGone()
            }
        }
    }

    override fun getItemCount(): Int = list.size - 1

    fun setData(newList: List<Asks>) {
        list = newList
        notifyDataSetChanged()
    }

}