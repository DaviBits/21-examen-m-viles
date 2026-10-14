package com.example.a21.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a21.model.Carta
import com.example.a21.model.Resultado
import com.example.a21.ui.theme.TemaApp
import com.example.a21.ui.theme._21Theme
import com.example.a21.viewmodel.JuegoViewModel

class GameScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tema = TemaApp.valueOf(
            intent.getStringExtra("tema") ?: TemaApp.CORAL.name
        )
        val nombreInicial = intent.getStringExtra("nombre") ?: ""

        setContent {
            val context=LocalContext.current
            _21Theme(tema = tema) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaJuego(
                        onSalir = {
                            val destino = Intent(
                                context,
                                MainScreen::class.java
                            ).apply {
                                putExtra("tema", tema.name)
                                putExtra("bandera", "regreso")
                                putExtra("nombre", nombreInicial)
                            }
                            context.startActivity(destino)
                        },nombre=nombreInicial,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaJuego(
    onSalir: () -> Unit,
    modifier: Modifier = Modifier,
    nombre: String,
    viewModel: JuegoViewModel = viewModel()
) {
    Column(
        horizontalAlignment=Alignment.CenterHorizontally,
        verticalArrangement=Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement=Arrangement.SpaceBetween,
            verticalAlignment=Alignment.CenterVertically
        ) {
            Text("OBJETIVO: ${viewModel.objetivo}",
                fontWeight = FontWeight.Bold
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("CRUPIER",
                fontSize=18.sp,
                fontWeight= FontWeight.Bold)
            Text(
                text=if(viewModel.terminada){
                    "${viewModel.puntosCrupier}"
                }else{
                    "${viewModel.puntosCrupier} + ?"
                },
                fontSize = 16.sp
            )
            Spacer(modifier=Modifier
                .height(8.dp))
            ManoGUI(
                cartas=viewModel.cartasCrupier,
                ocultarSegunda= !viewModel.terminada
            )
        }

        Box(
            modifier=Modifier
                .height(48.dp),
            contentAlignment=Alignment.Center
        ) {
            val res = viewModel.resultado
            if (res != null) {
                Text(
                    text = when (res) {
                        Resultado.GANA_JUGADOR -> "GANASTE :)"
                        Resultado.GANA_CRUPIER -> "PERDISTE :("
                        Resultado.EMPATE -> "EMPATE :l"
                    },
                    fontSize =28.sp,
                    fontWeight=FontWeight.Bold,
                    color =MaterialTheme.colorScheme.primary
                )
            }
        }

        Column(
            horizontalAlignment=Alignment.CenterHorizontally){
            ManoGUI(cartas=viewModel.cartasJugador)
            Spacer(modifier=Modifier.height(8.dp))
            Text("TÚ: ${viewModel.puntosJugador}",
                fontSize=18.sp,
                fontWeight=FontWeight.Bold
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally){
            if (viewModel.terminada) {
                Button(onClick = { viewModel.nuevaRonda() }) {
                    Text("NUEVA RONDA")
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    Button(
                        onClick={viewModel.pedirCarta()},
                        enabled=viewModel.puedePedir
                    ) {
                        Text("PEDIR")
                    }
                    Button(onClick ={viewModel.plantarse()}){
                        Text("PLANTARSE")
                    }
                }
            }
            TextButton(onClick = onSalir) {
                Text("VOLVER")
            }
        }
    }
}

@Composable
fun ManoGUI(
    cartas: List<Carta>,
    ocultarSegunda: Boolean = false,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement=Arrangement.spacedBy((-40).dp, Alignment.CenterHorizontally)
    ) {
        itemsIndexed(cartas){indice, carta ->
            CartaGUI(
                carta = carta,
                bocaArriba = !(ocultarSegunda && indice == 1)
            )
        }
    }
}