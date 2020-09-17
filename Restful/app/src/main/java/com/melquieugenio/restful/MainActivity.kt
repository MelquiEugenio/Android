package com.melquieugenio.restful

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private var user: String = "MelquiEugenio"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: RestService = retrofit.create(RestService::class.java)

        try {
            val repos: Call<List<Repo>?> = service.listRepos(user)

            repos.enqueue(object : Callback<List<Repo>?> {

                override fun onResponse(
                    call: Call<List<Repo>?>,
                    response: Response<List<Repo>?>
                ) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MyAdapter(response.body()!!, this@MainActivity)
                    }
                }

                override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

        } catch (e: Exception) {
            Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
        }

    }
}