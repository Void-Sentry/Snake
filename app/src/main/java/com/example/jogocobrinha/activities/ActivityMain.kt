package com.example.jogocobrinha.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jogocobrinha.R
import com.example.jogocobrinha.databinding.ActivityMainBinding
import com.example.jogocobrinha.models.JogoCobrinha
import com.example.jogocobrinha.viewmodels.MainViewModel
import com.google.gson.Gson

class ActivityMain : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    private val activityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                RESULT_OK ->{
                    val params = it.data?.extras
                    viewModel.setGame(params?.getSerializable("game") as JogoCobrinha)
                }
            }

            Toast.makeText(this,
                "velocidade -> ${this.viewModel.game.value!!.getVelocidade()} \n" +
                        "tamanho do mapa -> ${this.viewModel.game.value!!.getModo()}", Toast.LENGTH_LONG).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.button3.setOnClickListener {
            val intent = Intent(this, ActivityConfig::class.java)
            intent.putExtra("game", viewModel.game.value)
            activityForResult.launch(intent)
        }

        binding.button.setOnClickListener {
            val intent = Intent(this, ActivityJogo::class.java)
            intent.putExtra("game", viewModel.game.value)
            activityForResult.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        val gson = Gson()
        val preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE)

        val json = preferences.getString("game", "")
        if(json == "")
            viewModel.setGame(JogoCobrinha())
        else
            viewModel.setGame(gson.fromJson(json, JogoCobrinha::class.java))
    }

    override fun onStop() {
        super.onStop()
        val gson = Gson()
        val json = gson.toJson(viewModel.game.value)

        val preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("game", json)
        editor.apply()
    }
}