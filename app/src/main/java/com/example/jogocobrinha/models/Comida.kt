package com.example.jogocobrinha.models

import java.io.Serializable

class Comida(var x: Int, var y: Int): Serializable
{
    fun desenhaComida()
    {
        x = (0..30).random()
        y = (0..30).random()
    }
}