package com.example.jogocobrinha.models

import java.io.Serializable

class Ponto(var x: Int, var y: Int): Serializable
{
    fun subir() { y-- }

    fun descer() { y++ }

    fun direita() { x++ }

    fun esquerda() { x-- }
}