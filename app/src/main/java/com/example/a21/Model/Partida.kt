package com.example.a21.model

enum class Resultado {
    GANA_JUGADOR,
    GANA_CRUPIER,
    EMPATE
}

enum class EstadoPartida {
    EN_JUEGO,
    TERMINADA
}

class Partida(
    val reglas: Reglas = Reglas(),
    nombreJugador: String = "Jugador"
) {

    private val mazo = Mazo()
    val jugador = Jugador(nombreJugador, reglas.objetivo)
    val crupier = Jugador("Crupier", reglas.objetivo)

    var estado: EstadoPartida = EstadoPartida.EN_JUEGO
        private set

    var resultado: Resultado? = null
        private set

    init{
        repartirInicial() }

    private fun repartirInicial(){
        for(i in 1..2) {
            jugador.recibirCarta(mazo.sacarCarta())
            crupier.recibirCarta(mazo.sacarCarta())
        }

        if(jugador.mano.esBlackjack()||crupier.mano.esBlackjack()||jugador.mano.sePaso()){
            terminar()
        }
    }

    fun pedirCarta() {
        if(estado == EstadoPartida.TERMINADA || !jugador.puedePedir()) return
        jugador.recibirCarta(mazo.sacarCarta())
        if(jugador.mano.sePaso()){
            terminar()
        } else if (jugador.mano.puntos() == reglas.objetivo) {
            plantarse()
        }
    }

    fun plantarse() {
        if (estado == EstadoPartida.TERMINADA) return

        jugador.plantarse()
        jugarCrupier()
        terminar()
    }

    private fun jugarCrupier() {
        while (crupier.mano.puntos()<reglas.limiteCrupier) {
            crupier.recibirCarta(mazo.sacarCarta())
        }
        crupier.plantarse()
    }

    private fun terminar() {
        estado=EstadoPartida.TERMINADA
        resultado=calcularResultado()
    }

    private fun calcularResultado(): Resultado {
        val manoJugador=jugador.mano
        val manoCrupier=crupier.mano
        if (manoJugador.sePaso()) return Resultado.GANA_CRUPIER
        if (manoCrupier.sePaso()) return Resultado.GANA_JUGADOR

        if (manoJugador.esBlackjack() && manoCrupier.esBlackjack()) return Resultado.EMPATE
        if (manoJugador.esBlackjack()) return Resultado.GANA_JUGADOR
        if (manoCrupier.esBlackjack()) return Resultado.GANA_CRUPIER

        return when {
            manoJugador.puntos()>manoCrupier.puntos() -> Resultado.GANA_JUGADOR
            manoJugador.puntos() < manoCrupier.puntos() -> Resultado.GANA_CRUPIER
            else -> Resultado.EMPATE
        }
    }

    fun nuevaRonda() {
        jugador.nuevaRonda()
        crupier.nuevaRonda()
        mazo.reiniciar()
        estado = EstadoPartida.EN_JUEGO
        resultado = null
        repartirInicial()
    }
}