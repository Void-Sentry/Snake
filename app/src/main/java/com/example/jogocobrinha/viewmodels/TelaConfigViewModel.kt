package com.example.jogocobrinha.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jogocobrinha.models.JogoCobrinha

class TelaConfigViewModel: ViewModel()
{
    private var _game = MutableLiveData<JogoCobrinha>()
    var game: LiveData<JogoCobrinha> = _game
    var modo:MutableLiveData<String> = MutableLiveData<String>("Tamanho: Grande")
    var velocidade:MutableLiveData<String> = MutableLiveData<String>("Dificuldade: 1")

    fun setGame(game:JogoCobrinha)
    {
        this._game = MutableLiveData<JogoCobrinha>(game)
        this.game = this._game
        this._game.value?.let { setVelocidade(it.getVelocidade()) }
        this._game.value?.let { setModo(it.getModo()) }
    }

    fun getPontuacao(){ game.value!!.getPontuacao() }

    fun setVelocidade(i:Int)
    {
        _game.value!!.setVelocidade(i)

    }

    fun setModo(i:Int)
    {
        _game.value!!.setModo(i)
    }
}