package com.example.jogocobrinha.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toIcon
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jogocobrinha.R
import com.example.jogocobrinha.databinding.ActivityTelaJogoBinding
import com.example.jogocobrinha.models.Comida
import com.example.jogocobrinha.models.JogoCobrinha
import com.example.jogocobrinha.models.Ponto
import com.example.jogocobrinha.viewmodels.TelaDeJogoViewModel
import com.google.gson.Gson

class ActivityJogo: AppCompatActivity()
{
    lateinit var binding: ActivityTelaJogoBinding
    lateinit var viewModel: TelaDeJogoViewModel
    var jogo: JogoCobrinha = JogoCobrinha()
    var comida: Comida = jogo.getComida()
    var ponto: Ponto = jogo.getPonto()

    var tela = Array(jogo.getLinha()){
        arrayOfNulls<ImageView>(jogo.getColuna())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_jogo)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tela_jogo)
        viewModel = ViewModelProvider(this).get(TelaDeJogoViewModel::class.java)

        viewModel.setGame(intent.extras?.getSerializable("game") as JogoCobrinha)

        binding.telaDeJogo = viewModel
        binding.lifecycleOwner = this

        var telaCobra = binding.telaJogo
        telaCobra.rowCount = jogo.getLinha()
        telaCobra.columnCount = jogo.getColuna()

        val inflater = LayoutInflater.from(this)

        for(i in 0 until jogo.getLinha()){
            for(j in 0 until jogo.getColuna()){
                tela[i][j] = inflater.inflate(R.layout.tabuleirocobraimagem, telaCobra, false) as ImageView
                telaCobra.addView(tela[i][j])
            }
        }

        var cobra = jogo.getCobra()
        cobra.add(ponto)
        jogo.setCobra(cobra)

        rodarJogo()

        binding.apply{
            button14.setOnClickListener{
                val intent = Intent()
                intent.putExtra("game", viewModel.game.value)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onResume() { super.onResume() }

    override fun onStart()
    {
        super.onStart()
        val gson = Gson()
        val preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE)

        val json = preferences.getString("game", "")
        if(json == "")
            viewModel.setGame(JogoCobrinha())
        else
            viewModel.setGame(gson.fromJson(json, JogoCobrinha::class.java))

    }

    override fun onStop()
    {
        super.onStop()
        val gson = Gson()
        val json = gson.toJson(viewModel.game.value)

        val preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("game", json)
        editor.apply()
    }

    fun rodarJogo()
    {
        var flag: Boolean = true
        val cobra = jogo.getCobra()
        var pontuacao: Int = 0
        val fps: Long = 60L

        Thread{
            while(flag){
                Thread.sleep(fps)
                runOnUiThread{

                    for(i in 0 until jogo.getLinha()){
                        for(j in 0 until jogo.getColuna()){
                            tela[i][j]!!.setImageBitmap(Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888).apply {
                                eraseColor(Color.WHITE)
                            })
                        }
                    }

                    binding.apply {
                        button10.setOnClickListener { viewModel.game.value!!.setBotaoPressionado(1) }

                        button11.setOnClickListener { viewModel.game.value!!.setBotaoPressionado(2) }

                        button12.setOnClickListener { viewModel.game.value!!.setBotaoPressionado(3) }

                        button13.setOnClickListener { viewModel.game.value!!.setBotaoPressionado(4) }
                    }

                    for (i in 0 until cobra.size){
                        tela[15][10]!!.setImageBitmap(Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888).apply { eraseColor(Color.GREEN) })
                    }

                    var x = cobra.get(0).x
                    var y = cobra.get(0).y

                    tela[comida.x][comida.y]!!.setImageResource(R.drawable.white)
                    if(ponto.x != comida.x || ponto.y != comida.y)
                        cobra.removeAt(cobra.size - 1)
                    else
                    {
                        comida.desenhaComida()
                        tela[comida.x][comida.y]!!.setImageResource(R.drawable.white)
                        binding.textView.text = jogo.setPontuacao(pontuacao++).toString()
                    }

                    if(jogo.getBotaoSubir() == 1){ ponto.subir() }
                    else if(jogo.getBotaoDescer() == 2){ ponto.descer() }
                    else if(jogo.getBotaoDireita() == 3){ ponto.direita() }
                    else if(jogo.getBotaoEsquerda() == 4){ ponto.esquerda() }

                    var cabeca = Ponto(x, y)
                    cobra.add(cabeca)
                }
            }
        }
    }

    fun exibirComida(){

    }
}