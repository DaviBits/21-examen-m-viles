package com.example.a21.model

class RegistroPartida(
    val ganador: String,
    val perdedor: String,
    val manoGanadora: List<Carta>,
    val puntosManoGanadora: Int,
    val fechaHora: String,
    val empate: Boolean
) {
    fun textoManoGanadora(): String {
        return manoGanadora.joinToString(" ") + " = " + puntosManoGanadora
    }
}