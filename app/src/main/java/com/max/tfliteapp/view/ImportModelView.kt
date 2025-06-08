package com.max.tfliteapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import com.max.tfliteapp.databinding.ImportModelViewBinding

class ImportModelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private val binding = ImportModelViewBinding.inflate(LayoutInflater.from(context), this, true)
    var onImportModelClicked: (() -> Unit)? = null

    init {
        binding.importModelButton.setOnClickListener {
            onImportModelClicked?.invoke()
        }
    }
}