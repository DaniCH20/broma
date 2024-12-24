package com.example.villancicos

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
    private lateinit var playButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al ImageView y al ImageButton desde el layout
        val imageView = findViewById<ImageView>(R.id.imageView)
         playButton = findViewById(R.id.imageButton1)

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
                startDiagonalAnimation()
                mediaPlayer.start()
                animationDrawable.start() // Iniciar la animación
               

            }

        }
    }
    private fun startDiagonalAnimation() {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        val animatorX = ObjectAnimator.ofFloat(
            playButton,
            "x",
            0f,
            screenWidth - playButton.width.toFloat()
        )
        val animatorY = ObjectAnimator.ofFloat(
            playButton,
            "y",
            0f,
            screenHeight - playButton.height.toFloat()
        )

        // Alternar entre moverse hacia adelante y hacia atrás
        animatorX.repeatCount = ObjectAnimator.INFINITE
        animatorY.repeatCount = ObjectAnimator.INFINITE
        animatorX.repeatMode = ObjectAnimator.REVERSE
        animatorY.repeatMode = ObjectAnimator.REVERSE

        // Sincronizar duración y ejecución
        animatorX.duration = 3000L
        animatorY.duration = 3000L

        // Ejecutar animadores juntos
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animatorX, animatorY)
        animatorSet.start()
    }

}