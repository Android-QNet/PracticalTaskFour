package com.example.practicaltaskfour.utility

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.practicaltaskfour.R

class CustomComponentBottomMenu @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int, default: T) =
        getInt(index, -1).let {
            if (it >= 0) enumValues<T>()[it] else default
        }

    var drawable: Int = 0
    var drawableActive: Int = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.component_bottom_menu, this, true)

        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it, R.styleable.custom_component_tabs, 0, 0
            )

            var textLabel = typedArray.getString(
                R.styleable.custom_component_tabs_tabLabel
            )

            val isSelected = typedArray.getBoolean(
                R.styleable.custom_component_tabs_tabSelected, true
            )

            drawable = typedArray.getResourceId(
                R.styleable.custom_component_tabs_tabDrawable, R.drawable.ic_poll
            )

            drawableActive = typedArray.getResourceId(
                R.styleable.custom_component_tabs_tabDrawableActive,
                R.drawable.ic_poll
            )

            findViewById<TextView>(R.id.textLabel).text = textLabel
            findViewById<ImageView>(R.id.img).setImageResource(drawable)

            isTabSelected(isSelected)

            typedArray.recycle()
        }
    }

    fun setTextLabel(label: String){
        findViewById<TextView>(R.id.textLabel).text = label
    }

    fun isTabSelected(boolean: Boolean = false) {
        if (boolean) {
            findViewById<ConstraintLayout>(R.id.constraintImg).setBackgroundResource(R.drawable.bg_gray_rounded)
            findViewById<AppCompatTextView>(R.id.textLabel).setTextColor(context.getColor(R.color.black))
            findViewById<AppCompatImageView>(R.id.img).setImageResource(drawableActive)
        } else {
            findViewById<ConstraintLayout>(R.id.constraintImg).setBackgroundResource(R.drawable.bg_white_rounded)
            findViewById<AppCompatTextView>(R.id.textLabel).setTextColor(context.getColor(R.color.gray))
            findViewById<AppCompatImageView>(R.id.img).setImageResource(drawable)
        }
    }
}