package com.example.a21.model

enum class Palo(val simbolo: String, val esRojo: Boolean) {
    CORAZONES("♥", true),
    DIAMANTES("♦", true),
    TREBOLES("♣", false),
    PICAS("♠", false)
}

enum class Rango(val etiqueta: String, val valor: Int) {
    AS("A", 11),
    DOS("2", 2),
    TRES("3", 3),
    CUATRO("4", 4),
    CINCO("5", 5),
    SEIS("6", 6),
    SIETE("7", 7),
    OCHO("8", 8),
    NUEVE("9", 9),
    DIEZ("10", 10),
    JOTA("J", 10),
    REINA("Q", 10),
    REY("K", 10)
}

class Carta(val rango: Rango, val palo: Palo) {

    val valor: Int
        get() = rango.valor

    fun esAs(): Boolean = rango == Rango.AS

    override fun toString(): String = "${rango.etiqueta}${palo.simbolo}"
}