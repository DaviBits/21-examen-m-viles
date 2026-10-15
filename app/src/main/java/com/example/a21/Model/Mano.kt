package com.example.a21.model

class Mano(val objetivo: Int) {

    private val listaCartas = mutableListOf<Carta>()

    val cartas: List<Carta>
        get() = listaCartas

    fun agregar(carta: Carta){
        listaCartas.add(carta)
    }

    fun limpiar(){
        listaCartas.clear()
    }

    fun puntos(): Int {
        var total=0
        var asesComoOnce = 0

        for (carta in listaCartas) {
            total+=carta.valor
            if (carta.esAs()){
                asesComoOnce++
            }
        }

        while (total>objetivo&&asesComoOnce>0) {
            total -= 10
            asesComoOnce--
        }
        return total
    }

    fun sePaso(): Boolean = puntos() > objetivo

    fun esBlackjack(): Boolean = listaCartas.size == 2 && puntos() == objetivo
}