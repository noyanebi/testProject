package com.test.util.databinder

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.R

class GlideBindingAdapter {

    companion object {
        private val TAG = "GlideBindingAdapter"

        @JvmStatic
        @BindingAdapter("app:imageFromlocal")
        fun setImageResource(view: ImageView, imageUrl: Int?) {
            val context = view.context
            val options = RequestOptions().error(R.drawable.loading_banner)

            imageUrl?.let {
                Glide.with(context)
                        .setDefaultRequestOptions(options)
                        .load(imageUrl)
                        .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("app:imageFromUrl")
        fun setImageResource(view: ImageView, imageUrl: String?) {

            val context = view.context

            Log.d(TAG, "setMainImageResource: $imageUrl")
            val options = RequestOptions()
                    .error(R.drawable.loading_banner)
                    .placeholder(R.drawable.loading_banner)

            imageUrl?.let {
                Glide.with(context)
                        .setDefaultRequestOptions(options)
                        .load(imageUrl)
                        .into(view)
            }
        }
    }
}
