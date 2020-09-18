package com.melquieugenio.restful

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObservers()

    }

    private fun initObservers() {
        viewModel.getRepoList("MelquiEugenio").observe(this, {
            Log.d("MainActivity", "Repositories list updated!")
            handleResults(it!!)
        })

        viewModel.state.observe(this, {
            when (it) {
                is MainActivityState.OnLoaded -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBarLoading.visibility = View.GONE
                }
                is MainActivityState.OnLoading -> {
                    recyclerView.visibility = View.GONE
                    progressBarLoading.visibility = View.VISIBLE
                }
                is MainActivityState.OnError -> {
                    Log.d("MainActivity", "Error ${it.error}!")
                    handleError(it.error)
                    recyclerView.visibility = View.GONE
                    progressBarLoading.visibility = View.GONE
                }
            }
        })




    }

    private fun handleResults(list: List<Repo>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyAdapter(list, this@MainActivity) {
                Toast.makeText(context, "Clicked.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(
            this, "ERROR IN FETCHING API RESPONSE: ${t.message}. Try again",
            Toast.LENGTH_LONG
        ).show()
    }

}
