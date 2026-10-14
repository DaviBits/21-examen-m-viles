package com.example.a21.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.a21.model.OBJETIVO_CLASICO
import com.example.a21.model.OBJETIVO_MAXIMO
import com.example.a21.model.OBJETIVO_MINIMO
import com.example.a21.ui.theme.TemaApp
import com.example.a21.ui.theme._21Theme

class SeleccionScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tema = TemaApp.valueOf(
            intent.getStringExtra("tema") ?: TemaApp.CORAL.name
        )
        val nombreInicial = intent.getStringExtra("nombre") ?: ""


        setContent {

            _21Theme(tema = tema) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaSeleccion(
                        onJugar = { objetivo ->
                            val destino = Intent(this@SeleccionScreen, GameScreen::class.java).apply {
                                putExtra("tema", tema.name)
                                putExtra("objetivo", objetivo)
                                putExtra("nombre", nombreInicial)
                            }
                            startActivity(destino)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaSeleccion(onJugar: (Int) -> Unit, modifier: Modifier = Modifier) {
    var objetivo by remember{ mutableIntStateOf(OBJETIVO_CLASICO)}
    Column(
        horizontalAlignment=Alignment.CenterHorizontally,
        verticalArrangement=Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text("ELIGE EL VALOR", fontSize = 24.sp)
        Text("$objetivo", fontSize = 72.sp)
        Button(
            onClick={ objetivo=objetivo+1},
            enabled=objetivo<OBJETIVO_MAXIMO
        ) {
            Text("AUMENTAR")
        }

        Button(
            onClick={objetivo= objetivo-1},
            enabled=objetivo>OBJETIVO_MINIMO
        ) {
            Text("DISMINUIR")
        }

        Button(onClick={onJugar(objetivo) }){
            Text("JUGAR")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeleccionPreview() {
    _21Theme {
        PantallaSeleccion(onJugar={})
    }
}