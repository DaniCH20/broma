package com.example.villancicos

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.TransitionDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al ImageView y al ImageButton desde el layout
        val imageView = findViewById<ImageView>(R.id.imageView)
        val playButton = findViewById<ImageButton>(R.id.imageButton1)

        // Configura el drawable como AnimationDrawable
        imageView.setBackgroundResource(R.drawable.transition) // Nota: usa setBackgroundResource
        animationDrawable = imageView.background as AnimationDrawable
        mediaPlayer = MediaPlayer.create(this, R.raw.navidad)
        // Listener para iniciar la animación al presionar el botón
        playButton.setOnClickListener {
            if (mediaPlayer.isPlaying || animationDrawable.isRunning) {//Si esta funcionando lo para y cambia la imagen del boton
                mediaPlayer.pause()
                animationDrawable.stop() // Detener la animación si ya está corriendo
            } else {//Si no, inicia la cancion y cambia la imagen del boton
                mediaPlayer.start()
                animationDrawable.start() // Iniciar la animación
                playButton.setImageResource(R.drawable.pause)

            }

        }
    }

}