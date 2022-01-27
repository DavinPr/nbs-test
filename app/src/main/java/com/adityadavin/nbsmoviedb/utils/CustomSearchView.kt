package com.adityadavin.nbsmoviedb.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.adityadavin.nbsmoviedb.R

class CustomSearchView : SearchView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        val poppinsFont = ResourcesCompat.getFont(context, R.font.poppins)
        val textView = findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        textView?.apply {
            typeface = poppinsFont
            textSize = 14f
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setHintTextColor(ContextCompat.getColor(context, R.color.grey_500))
            queryHint = resources.getString(R.string.search_hint)
        }


        val closeIcon = findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeIcon.setColorFilter(ResourcesCompat.getColor(resources, R.color.yellow_600, null))

        val searchIcon = findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(ResourcesCompat.getColor(resources, R.color.grey_500, null))
    }
}