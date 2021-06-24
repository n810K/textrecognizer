package com.n810.textrecognition

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.*
import com.n810.TextRecognition.BuildConfig
import com.n810.TextRecognition.R
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    var capturedImagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        capture_button.setOnClickListener {

            val fileName = "capturedImage"
            val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            var capturedImage = File.createTempFile(fileName, ".jpg", storageDirectory)
            capturedImagePath = capturedImage.absolutePath
            var outputFileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + "." + localClassName + ".fileprovider", capturedImage)
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            captureImageLauncher.launch(intent)
        }

        load_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            loadImageLauncher.launch(intent)
        }

        convert_button.setOnClickListener {
            if (image_viewer.drawable != null) {
                val imageBitmap = InputImage.fromBitmap((image_viewer.drawable as BitmapDrawable).bitmap, 0)
                val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                val result = recognizer.process(imageBitmap)
                    .addOnSuccessListener {
                            text_viewer.text = it.text
                        }
                    .addOnFailureListener {
                        text_viewer.text = "Failed to Detect Text"
                        Toast.makeText(this, "Failed to Detect Text", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this@MainActivity, "No Image Loaded", Toast.LENGTH_SHORT).show()
            }
        }

    }


    var captureImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result?.resultCode == RESULT_OK) {
            var imageBitmap = BitmapFactory.decodeFile(capturedImagePath) as Bitmap
            image_viewer.setImageBitmap(imageBitmap)
        } else {
            Toast.makeText(this@MainActivity,"Failed to Capture Image", Toast.LENGTH_SHORT).show()
        }
    }

    var loadImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result?.resultCode == Activity.RESULT_OK) {
            val image = result.data?.data
            image_viewer.setImageURI(image)
        } else {
            Toast.makeText(this@MainActivity,"Failed to Load Image", Toast.LENGTH_SHORT).show()
        }
    }




}


