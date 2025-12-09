package com.example.villancicos

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: ImageButton
    var ejecucion: Boolean = false
    private var initialX = 0f
    private var initialY = 0f
    private var jobMovimiento: Job? = null
    private val imagenes = arrayOf(
        R.drawable.file,
        R.drawable.file2,
        R.drawable.file3,
        R.drawable.file4,
        R.drawable.file5,
        R.drawable.file6,
        R.drawable.file7,
        R.drawable.file8,
        R.drawable.file9,
        R.drawable.file10,
        R.drawable.file11,
        R.drawable.file12
    )
    private var posicion = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gif = findViewById<ImageView>(R.id.imageView6)

        Glide.with(this)
            .asGif()
            .load(R.drawable.fondo)  // tu gif en /res/drawable/
            .into(gif)

        val imageView = findViewById<ImageView>(R.id.imageView)
         playButton = findViewById(R.id.imageButton1)
        playButton.post {
            initialX = playButton.x
            initialY = playButton.y
        }

        imageView.setBackgroundResource(R.drawable.transition)
        animationDrawable = imageView.background as AnimationDrawable
        mediaPlayer = MediaPlayer.create(this, R.raw.navidad)


        playButton.setOnClickListener {

            if(!ejecucion){
                startDiagonalAnimation()
                mediaPlayer.start()
                animationDrawable.start()
                ejecucion = true
            }else{
                stopDiagonalAnimation()
                mediaPlayer.pause()
                animationDrawable.stop()
                ejecucion = false
            }


        }
    }
    private fun startDiagonalAnimation() {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        var x = playButton.x
        var y = playButton.y
        var vx = 6f   // velocidad en X
        var vy = 8f   // velocidad en Y

        jobMovimiento = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                x += vx
                y += vy

                var rebotado = false // para saber si hubo choque

                // 🔹 Rebote en X
                if (x <= 0 || x + playButton.width >= screenWidth) {
                    vx = -vx
                    rebotado = true
                }

                // 🔹 Rebote en Y
                if (y <= 0 || y + playButton.height >= screenHeight) {
                    vy = -vy
                    rebotado = true
                }

                // 🔹 Si rebotó, cambia la imagen
                if (rebotado) cambiarImagen()

                playButton.x = x
                playButton.y = y

                delay(16L)
            }
        }
    }
    private fun cambiarImagen() {
        posicion = (posicion + 1) % imagenes.size
        playButton.setImageResource(imagenes[posicion])
    }
    private fun stopDiagonalAnimation() {
        jobMovimiento?.cancel()


        val backX = ObjectAnimator.ofFloat(playButton, "x", playButton.x, initialX)
        val backY = ObjectAnimator.ofFloat(playButton, "y", playButton.y, initialY)
        AnimatorSet().apply {
            playTogether(backX, backY)
            duration = 1000
            start()
        }
    }
}