package com.example.a21.model

const val OBJETIVO_CLASICO = 21
const val OBJETIVO_MINIMO = 15
const val OBJETIVO_MAXIMO = 31

class Reglas(val objetivo: Int = OBJETIVO_CLASICO) {

    val limiteCrupier: Int
        get()=objetivo-4
}