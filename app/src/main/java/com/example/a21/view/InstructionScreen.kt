package com.example.a21.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a21.ui.theme.TemaApp
import com.example.a21.ui.theme._21Theme

class InstruccionesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val tema = TemaApp.valueOf(
            intent.getStringExtra("tema") ?: TemaApp.CORAL.name
        )
        setContent {
            _21Theme (tema=tema){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaInstrucciones(tema, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaInstrucciones(tema: TemaApp, modifier: Modifier = Modifier) {
    val context=LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text="INSTRUCCIONES",
                fontSize=28.sp,
                fontWeight=FontWeight.Bold,
                color=MaterialTheme.colorScheme.primary,
                textAlign=TextAlign.Center,
                modifier=Modifier.fillMaxWidth()
            )
        }
        item {
            TarjetaInstruccion(
                titulo= "Objetivo",
                puntos=listOf(
                    "El jugador compite unicamente con el crupier.",
                    "Gana quien obtenga blackjack o la puntuación más alta sin pasarse del objetivo."
                )
            )
        }
        item {
            TarjetaInstruccion(
                titulo="Valor de las cartas",
                puntos=listOf(
                    "El As vale 11 o 1, según convenga para no pasarse del objetivo.",
                    "Las figuras (J, Q, K) valen 10.",
                    "Las cartas numéricas valen su número."
                )
            )
        }
        item {
            TarjetaInstruccion(
                titulo="Reparto de cartas ",
                puntos=listOf(
                    "El crupier reparte dos cartas visibles a cada jugador.",
                    "El crupier se queda con su primera carta boca arriba y la segunda boca abajo.")
            )
        }

        item {
            TarjetaInstruccion(
                titulo="Blackjack",
                puntos=listOf(
                    "Un As junto con una carta de valor 10 es blackjack y se gana automáticamente.",
                    "Si el crupier también tiene blackjack, es empate.",
                    "Llegar a 21 con más de dos cartas no es blackjack: es una jugada inferior."
                )
            )
        }

        item {
            TarjetaInstruccion(
                titulo="Turno del jugador",
                puntos=listOf(
                    "Puedes plantarte y quedarte con tu puntuación actual \n o pedir mas cartas",
                    "Si al pedir carta se pasa de 21, pierde automáticamente."
                )
            )
        }

        item {
            TarjetaInstruccion(
                titulo="Turno del crupier",
                puntos=listOf(
                    "Cuando todos los jugadores terminan, el crupier muestra su carta boca abajo.",
                    "Saca cartas hasta sumar 17 o más puntos, y entonces se planta."
                )
            )
        }

        item {
            TarjetaInstruccion(
                titulo="Resultado final",
                puntos=listOf(
                    "Gana quien tenga blackjack o la puntuación más alta sin pasarse del objetivo.",
                )
            )
        }
        
        item{
            Button(onClick= {
                val destino = Intent(
                    context,
                    MainScreen::class.java
                ).apply {
                    putExtra("tema", tema.name)
                    putExtra("bandera", "regreso")
                }
                context.startActivity(destino)
            }) {
                Text("volver")
            }
        }
    }
}

@Composable
fun TarjetaInstruccion(titulo: String, puntos: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text=titulo,
                fontSize=20.sp,
                fontWeight=FontWeight.Bold
            )
            Spacer(modifier=Modifier.height(8.dp))
            puntos.forEach { punto ->
                Row(modifier=Modifier.padding(vertical = 3.dp)) {
                    Text("•  ", fontSize = 16.sp)
                    Text(
                        text=punto,
                        fontSize=16.sp,
                        modifier=Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InstruccionesPreview() {
    _21Theme {
        PantallaInstrucciones(TemaApp.CASINO)
    }
}