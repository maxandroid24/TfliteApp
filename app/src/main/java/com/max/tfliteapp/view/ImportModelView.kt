package com.max.tfliteapp.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import com.max.tfliteapp.R
import com.max.tfliteapp.databinding.ImportModelViewBinding

class ImportModelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private val binding = ImportModelViewBinding.inflate(LayoutInflater.from(context), this, true)
    var onImportModelClicked: (() -> Unit)? = null
    var onImportLabelClicked: (() -> Unit)? = null

    init {
        binding.root.setTransitionListener(object : TransitionListener {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                Log.d("ImportModelView", "Transition complete to $currentId")
                when (currentId) {
                    R.id.end -> {
                        onImportModelClicked?.invoke()
                    }
                    R.id.end_label -> {
                        onImportLabelClicked?.invoke()
                    }
                    else -> {
                        Log.d("ImportModelView", "Transitioned to unknown state: $currentId")
                    }
                }
            }

            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                Log.d("ImportModelView", "Transition started from $startId to $endId")
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                Log.d("ImportModelView", "Transition changed from $startId to $endId with progress $progress")
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                TODO("Not yet implemented")
            }
        })
    }

    fun resetTransition() {
        Log.d("ImportModelView", "Transitioning to start state")
        binding.root.transitionToStart()
    }

}