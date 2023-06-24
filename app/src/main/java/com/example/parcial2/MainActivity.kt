package com.example.parcial2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var textViewJoke: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewJoke = findViewById(R.id.textViewJoke)
        getRandomJoke()

    }

    private fun getRandomJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getRandomJoke("random")
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val joke = response?.joke
                    if (joke != null) {
                        textViewJoke.text=joke
                    }
                } else {
                    showError()
                }
            }
        }
    }


    private fun showError() {
        Toast.makeText(this, "fallo en la llamada", Toast.LENGTH_SHORT).show()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}