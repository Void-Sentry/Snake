package com.example.jogocobrinha.models

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class JogoCobrinha: Serializable
{
    // Jogo
    private var pontuacao: Int = 0
    private var maiorPontuacao: Int = 0

    // Tabuleiro
    private var linha: Int = 40
    private var coluna: Int = 60
    private var modo: Int = 1
    private var ponto = Ponto(30, 30)

    // Controles
    private var subir: Int? = null
    private var descer: Int? = null
    private var direita: Int? = null
    private var esquerda: Int? = null

    // Cobra
    private var cobra: ArrayList<Ponto> = ArrayList<Ponto>()
    private var velocidade:Int = 1

    // Comida
    private var comida: Comida = Comida(8, 8)

    // Nome
    private lateinit var nome: String

    fun getPontuacao(): Int{ return this.pontuacao }

    fun setPontuacao(pontuacao: Int) { this.pontuacao = pontuacao }

    fun getModo(): Int { return modo }

    fun setModo(modo:Int){ this.modo = modo }

    fun getVelocidade(): Int { return velocidade }

    fun setVelocidade(velocidade:Int){ this.velocidade = velocidade }

    fun setNome(nome:String){ this.nome = nome }

    fun getNome(): String{ return this.nome }

    fun getLinha(): Int{ return this.linha }

    fun setLinha(linha: Int){ this.linha = linha }

    fun getColuna(): Int{ return this.coluna }

    fun setColuna(coluna: Int){ this.coluna = coluna }

    fun getCobra(): ArrayList<Ponto>{ return this.cobra }

    fun setCobra(cobra: ArrayList<Ponto>){ this.cobra = cobra }

    fun getBotaoSubir(): Int?{
        return this.subir
    }

    fun setBotaoSubir(subir: Int){
        this.subir = subir
    }

    fun getBotaoDescer(): Int?{
        return this.descer
    }

    fun setBotaoDescer(descer: Int){
        this.descer = descer
    }

    fun getBotaoDireita(): Int?{
        return this.direita
    }

    fun setBotaoDireita(direita: Int){
        this.direita = direita
    }

    fun getBotaoEsquerda(): Int?{
        return this.esquerda
    }

    fun setBotaoEsquerda(esquerda: Int){
        this.esquerda = esquerda
    }

    fun setBotaoPressionado(controle: Int){
        when(controle)
        {
            1 -> this.subir = controle
            2 -> this.descer = controle
            3 -> this.direita = controle
            4 -> this.esquerda = controle
        }
    }

    fun getComida(): Comida { return this.comida }

    fun setComida(comida: Comida) { this.comida = comida }

    fun getPonto(): Ponto { return this.ponto }

    fun setPonto(ponto: Ponto){ this.ponto = ponto }
}