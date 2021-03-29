package com.example.chuibbo_android.image

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.synthesis_item_list.view.*

class Adapter : RecyclerView.Adapter<Adapter.ItemViewHolder>() {
    private var itemlist = emptyList<Image>()

    inner class ItemViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun bind(image: Image) {
            BitmapFactory.decodeFile(image.image)?.also { bitmap ->
                itemView.img_synthesis.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.synthesis_item_list,parent,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemlist[position])
    }

    fun addCategoryList(item : List<Image>){
        this.itemlist = item
        notifyDataSetChanged()
    }
}