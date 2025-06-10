package com.max.tfliteapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.max.tfliteapp.databinding.ActivityMainBinding
import com.max.tfliteapp.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ImportModelView"
    }

    private val viewModel: MainActivityViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ImportModelView", "onCreate called")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    override fun onResume() {
        super.onResume()
        binding.importModelView.resetTransition()
        Log.i("ImportModelView", "onResume called")
    }

    private fun initView() {
        Log.d(TAG, "onImportModelClicked callback triggered")
        binding.importModelView.onImportModelClicked = {
            modelPickerLauncher.launch(arrayOf("application/octet-stream", "model/tflite"))
        }
    }

    private val modelPickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        Log.d(TAG, "Model file selected: $uri")
        uri?.let {
            // Access and use the selected TFLite file
            contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            // Now you can use the URI, e.g., copy to local storage or load in TFLite
            Log.d(TAG, "Selected file: $uri")
        }
    }
}