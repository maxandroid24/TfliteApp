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
        binding.importModelView.onImportModelClicked = {
            Log.d(TAG, "onImportModelClicked callback triggered")
            modelPickerLauncher.launch(arrayOf("application/octet-stream", "model/tflite"))
        }
        binding.importModelView.onImportLabelClicked = {
            Log.d(TAG, "onImportLabelClicked callback triggered")
            labelPickerLauncher.launch(arrayOf("text/plain"))
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
//            val intent = Intent(this, OutputActivity::class.java).apply {
//                putExtra("modelUri", it.toString())
//            }
            binding.modelNameText.text = getFileNameFromUri(it)
//            binding.labelNameText.text = it.toString()
            // Now you can use the URI, e.g., copy to local storage or load in TFLite
            Log.d(TAG, "Selected file: $uri")
        }
    }

    private val labelPickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        Log.d(TAG, "Label file selected: $uri")
        uri?.let {
            // Access and use the selected TFLite file
            contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
//            val intent = Intent(this, OutputActivity::class.java).apply {
//                putExtra("modelUri", it.toString())
//            }
            binding.labelNameText.text = getFileNameFromUri(it)
//            binding.labelNameText.text = it.toString()
            // Now you can use the URI, e.g., copy to local storage or load in TFLite
            Log.d(TAG, "Selected file: $uri")
        }
    }

    private fun getFileNameFromUri(uri: Uri): String {
        var result = "unknown_file"
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex("_display_name")
                if (nameIndex != -1) {
                    result = it.getString(nameIndex)
                }
            }
        }
        return result
    }

}
