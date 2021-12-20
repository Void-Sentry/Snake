package com.example.jogocobrinha.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jogocobrinha.R
import com.example.jogocobrinha.databinding.ActivityTelaConfiguracaoBinding
import com.example.jogocobrinha.models.JogoCobrinha
import com.example.jogocobrinha.viewmodels.TelaConfigViewModel
import com.google.gson.Gson

class ActivityConfig: AppCompatActivity() {
    lateinit var binding: ActivityTelaConfiguracaoBinding
    lateinit var viewModel: TelaConfigViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_configuracao)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tela_configuracao)
        viewModel = ViewModelProvider(this).get(TelaConfigViewModel::class.java)

        binding.telaConfigViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setGame(intent.extras?.getSerializable("game") as JogoCobrinha)

        binding.button9.setOnClickListener {
            val intent = Intent()
            intent.putExtra("game", viewModel.game.value)
            setResult(Activity.RESULT_OK, intent)
            finish()
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
        when(this.viewModel.game.value!!.getVelocidade()) {
            1 -> this.binding.radioButton.toggle()
            2 -> this.binding.radioButton3.toggle()
            3 -> this.binding.radioButton4.toggle()
        }

        when(this.viewModel.game.value!!.getModo()){
            1 -> this.binding.radioButton5.toggle()
            2 -> this.binding.radioButton6.toggle()
        }
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