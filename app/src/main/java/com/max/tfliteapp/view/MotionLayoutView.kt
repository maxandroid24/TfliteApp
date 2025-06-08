package com.max.tfliteapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import com.max.tfliteapp.databinding.MotionLayoutViewBinding

class MotionLayoutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    val binding = MotionLayoutViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {

    }

}