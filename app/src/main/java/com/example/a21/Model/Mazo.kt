package com.example.a21.model

class Mazo {

    private val cartas = mutableListOf<Carta>()

    init{
        generar()
        mezclar()}

    private fun generar(){
        cartas.clear()
        for (palo in Palo.entries) {
            for (rango in Rango.entries) {
                cartas.add(Carta(rango, palo))
            }
        }
    }

    fun mezclar(){
        cartas.shuffle()
    }

    fun sacarCarta(): Carta {
        if (cartas.isEmpty()) {
            reiniciar()
        }
        return cartas.removeAt(cartas.size - 1)
    }


    fun reiniciar(){
        generar()
        mezclar()
    }
}