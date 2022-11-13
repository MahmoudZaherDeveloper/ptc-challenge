package com.ptc.challenge.presentation

import android.graphics.Paint
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ptc.challenge.R


@BindingAdapter("src")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide
            .with(view.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade()) //fading animation

            .error(R.drawable.placeholder_img)
            .placeholder(R.drawable.placeholder_img)
            .into(view);

    }
}

@BindingAdapter("strike")
fun TextView.strikeThrough(strikeThrough: Boolean = true) {
    this.paintFlags = this.paintFlags or
            if (strikeThrough) {
                Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
}