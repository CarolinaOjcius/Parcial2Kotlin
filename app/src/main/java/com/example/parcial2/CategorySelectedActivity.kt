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

class CategorySelectedActivity : AppCompatActivity() {
    private lateinit var textViewCategorySelected: TextView
    private lateinit var textViewJoke: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_selected)

        textViewCategorySelected= findViewById(R.id.textViewCategorySelected)
        textViewJoke= findViewById(R.id.textViewCategoryJoke)

        val bundle = intent.extras
        val category = bundle?.getString("category", "")
        textViewCategorySelected.text = category.toString().uppercase()

        getByCategory(category)
    }

    private fun getByCategory(category: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getByCategory("random?category=$category")
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val joke = response?.value
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
        Toast.makeText(this, "Failed to make API call", Toast.LENGTH_SHORT).show()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}