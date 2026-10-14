package com.example.a21.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.a21.TinyDB
import com.example.a21.model.Carta
import com.example.a21.model.EstadoPartida
import com.example.a21.model.OBJETIVO_CLASICO
import com.example.a21.model.OBJETIVO_MAXIMO
import com.example.a21.model.OBJETIVO_MINIMO
import com.example.a21.model.Partida
import com.example.a21.model.RegistroPartida
import com.example.a21.model.Reglas
import com.example.a21.model.Resultado
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class JuegoViewModel(application: Application,  savedStateHandle: SavedStateHandle): AndroidViewModel(application) {
    private val tinyDB = TinyDB(application)
    val nombreJugador: String = savedStateHandle.get<String>("nombre") ?: "Jugador"

    val objetivo: Int = (savedStateHandle.get<Int>("objetivo") ?: OBJETIVO_CLASICO)

    private val partida = Partida(Reglas(objetivo), nombreJugador)
    private var resultadoRegistrado = false

    var cartasJugador by mutableStateOf(listOf<Carta>())
        private set
    var cartasCrupier by mutableStateOf(listOf<Carta>())
        private set
    var puntosJugador by mutableIntStateOf(0)
        private set
    var puntosCrupier by mutableIntStateOf(0)
        private set
    var terminada by mutableStateOf(false)
        private set
    var puedePedir by mutableStateOf(false)
        private set
    var resultado by mutableStateOf<Resultado?>(null)
        private set
    var ganadas by mutableIntStateOf(0)
        private set
    var perdidas by mutableIntStateOf(0)
        private set
    var empates by mutableIntStateOf(0)
        private set

    init {
        actualizar()
    }

    fun pedirCarta() {
        partida.pedirCarta()
        actualizar()
    }
    fun plantarse() {
        partida.plantarse()
        actualizar()
    }

    fun nuevaRonda() {
        partida.nuevaRonda()
        resultadoRegistrado = false
        actualizar()
    }


    fun registrar(res: Resultado){
        val ganoElJugador = res!=Resultado.GANA_CRUPIER
        val quienGano = if (ganoElJugador) partida.jugador else partida.crupier
        val quienPerdio = if (ganoElJugador) partida.crupier else partida.jugador

        var registro = RegistroPartida(
            ganador = quienGano.nombre,
            perdedor = quienPerdio.nombre,
            manoGanadora = quienGano.mano.cartas.toList(),
            puntosManoGanadora = quienGano.mano.puntos(),
            fechaHora = fechaHoraActual(),
            empate = res == Resultado.EMPATE
        )

        val guardados = tinyDB.getListObject("registros", RegistroPartida::class.java)
            .filterIsInstance<RegistroPartida>()
        val lista = ArrayList<Any>(guardados)
        lista.add(registro)
        tinyDB.putListObject("registros", lista)



    }
    private fun fechaHoraActual(): String {
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return formato.format(Date())
    }


    private fun actualizar() {
        val termino = partida.estado == EstadoPartida.TERMINADA

        val res = partida.resultado
        if (res != null && !resultadoRegistrado) {
            //marcador.registrar(res)
            registrar(res)
            resultadoRegistrado = true


        }

        cartasJugador=partida.jugador.mano.cartas.toList()
        cartasCrupier=partida.crupier.mano.cartas.toList()
        puntosJugador=partida.jugador.mano.puntos()

        puntosCrupier=if (termino) {
            partida.crupier.mano.puntos()
        } else {
            partida.crupier.mano.cartas[0].valor
        }

        terminada=termino
        puedePedir=!termino && partida.jugador.puedePedir()
        resultado=res



    }
}