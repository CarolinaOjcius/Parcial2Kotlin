package com.example.parcial2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CategoriesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChuckNorrisAdapter
    private var categoriesList: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        getCategories()
        recyclerView = findViewById(R.id.RecyclerViewCategories)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ChuckNorrisAdapter(categoriesList)
        recyclerView.adapter = adapter



        adapter.onItemClickListener = { categoriesList  ->
            val intent = Intent(this, CategorySelectedActivity::class.java)
            intent.putExtra("category", categoriesList)
            startActivity(intent)
        }

    }

    private fun getCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getCategories("categories")
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val categories = response
                    if (categories != null) {
                        categoriesList = categories
                        adapter.notifyDataSetChanged()
                        adapter.submitList(categoriesList)

                    } else {
                        showError()
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

