package com.max.tfliteapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OutputActivity : AppCompatActivity() {

    companion object {
        const val TAG = "OutputActivity"
        private const val modelUriString = "modelUri"
        private const val labelUriString = "labelUri"
    }

    private var modelUri: Uri? = null
    private var labelUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_output)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        modelUri = getUriFromIntent(modelUriString)
        Log.i(TAG, "Model URI: $modelUri")
        labelUri = getUriFromIntent(labelUriString)
        Log.i(TAG, "Label URI: $labelUri")
    }

    private fun getUriFromIntent(uriString: String): Uri? {
        return Uri.parse(intent.getStringExtra(uriString))
    }
}