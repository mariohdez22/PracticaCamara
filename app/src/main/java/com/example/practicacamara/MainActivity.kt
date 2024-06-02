package com.example.practicacamara

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var capturar : ImageView
    private lateinit var urlImg : Uri
    private lateinit var tiempo : String
    private  lateinit var nombreImagen : String

    private var contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
        capturar.setImageURI(null)
        capturar.setImageURI(urlImg)
    }

    private fun createImagenUri() : Uri {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            tiempo = DateTimeFormatter.ofPattern("yyyMMdd_HHmmss").withZone(ZoneOffset.UTC).format(
                Instant.now())
        }

        nombreImagen = "$tiempo.png"

        val imagen = File(filesDir,nombreImagen)

        return FileProvider.getUriForFile(this, "com.sv.edu.ufg.fis.amb.contentprovider.FileProvider", imagen)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        capturar = findViewById(R.id.contenedorImagen)
        val capturarImgBtn = findViewById<Button>(R.id.btn_captura)

        capturarImgBtn.setOnClickListener{
            urlImg = createImagenUri()
            contract.launch(urlImg)
        }

    }

}