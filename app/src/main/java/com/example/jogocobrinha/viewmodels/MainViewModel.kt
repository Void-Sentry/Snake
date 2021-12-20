package com.example.jogocobrinha.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jogocobrinha.models.JogoCobrinha

class MainViewModel: ViewModel()
{
    private var _game = MutableLiveData<JogoCobrinha>()
    var game: LiveData<JogoCobrinha> = _game

    fun setGame(game:JogoCobrinha){
        this._game = MutableLiveData<JogoCobrinha>(game)
        this.game = this._game
    }

    fun getVelocidade(){ game.value!!.getVelocidade() }

    fun adicionarPontuacao(){ game.value!!.getPontuacao() }

    fun mudarNome(s: String) { _game.value?.setNome(s) }
}