package com.example.memeapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MemeFetch()

    }

    private fun MemeFetch() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        progress_bar.visibility = View.VISIBLE
        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_bar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_bar.visibility = View.GONE
                        return false
                    }

                }).into(memeImageView)
                // Display the first 500 characters of the response string.

            },
            {
                Toast.makeText(this, "Unable to load", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)


    }

    fun ShareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Checkout this cool meme sent through Meme App \n $currentImageUrl"
        )
        intent.type="text/plain"
        val chooser=Intent.createChooser(intent," Share meme using: ")
        startActivity(intent)

    }

    fun NextMeme(view: View) {
        MemeFetch()

    }
}