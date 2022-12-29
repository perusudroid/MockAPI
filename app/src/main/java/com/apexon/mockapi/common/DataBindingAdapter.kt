package com.apexon.mockapi.common

import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:pic")
    fun setImage(imageView: AppCompatImageView, url : String?) {
        Log.d("BindingAdapter", "setImage: url $url")

        val options = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(10))
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(imageView.context)
            .load(url)
            .apply(options)
            .into(imageView)
    }

}