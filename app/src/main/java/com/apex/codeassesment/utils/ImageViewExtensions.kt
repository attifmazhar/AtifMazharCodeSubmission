package com.apex.codeassesment.utils

import android.widget.ImageView
import com.apex.codeassesment.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * loads the url into the imageview
 *
 * shows an error placeholder or a loading placeholder when needed
 *
 * takes the image Url (nullable [String]) as a parameter
 */
fun ImageView.loadImage(url: String?) {

    val options = RequestOptions()
        .error(R.drawable.ic_image_not_found) // if the image can't be image can't be loaded icon
        .placeholder(R.drawable.ic_image_downloading) // show download icon when image is loading


    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}