package com.example.sportsscheduler.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

/**
Method to get the correct Time format
 */
@BindingAdapter("isoDateToFormattedDate")
fun TextView.setFormattedDateFromIso(timestamp: String?) {
    timestamp?.let {
        val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())

        try {
            val date = isoDateFormat.parse(timestamp)
            val formattedDate = outputDateFormat.format(date)
            text = formattedDate
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, triCode: String?) {
    triCode?.let {
        val imageUrl =
            Constant.IMAGE_BASE_URL+ triCode + Constant.IMAGE_END
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}
