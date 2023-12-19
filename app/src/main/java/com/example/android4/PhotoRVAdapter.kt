package com.example.android4

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import java.io.FileNotFoundException
import java.io.InputStream

class PhotoRVAdapter(
    var data: MutableList<PhotoItem>,
    val appContext: Context
): RecyclerView.Adapter<PhotoRVAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.ivPhotoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.photolayout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun changeData(newList: MutableList<PhotoItem>) {
        this.data = newList
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = data[position]
        holder.apply {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                image.setImageBitmap(appContext.contentResolver.loadThumbnail(
                    currentItem.uri,
                    Size(125, 125),
                    null
                ))
            } else {
                image.setImageBitmap(getBitmapFromUri(appContext, currentItem.uri))
            }
        }
        holder.itemView.setOnClickListener{ _ ->
            val bundle = Bundle().apply {
                putInt("photoPos", position)
            }
            Navigation.findNavController(holder.itemView).navigate(R.id.editToSlider, bundle)
        }
    }

    private fun getBitmapFromUri(mContext: Context, uri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val image_stream: InputStream
            try {
                image_stream = uri?.let {
                    mContext.contentResolver.openInputStream(it)
                }!!
                bitmap = BitmapFactory.decodeStream(image_stream)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}