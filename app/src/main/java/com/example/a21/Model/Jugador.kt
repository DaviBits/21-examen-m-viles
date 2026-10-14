package com.example.a21.model

class Jugador(val nombre: String, objetivo: Int) {

    val mano = Mano(objetivo)

    var sePlanto: Boolean = false
        private set

    fun recibirCarta(carta: Carta) {
        mano.agregar(carta)
    }

    fun plantarse() {
        sePlanto = true
    }

    fun puedePedir(): Boolean{
      return !sePlanto && mano.puntos()<mano.objetivo
    }

    fun nuevaRonda() {
        mano.limpiar()
        sePlanto = false
    }
}