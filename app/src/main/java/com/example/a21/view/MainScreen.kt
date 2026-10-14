package com.example.a21.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a21.ui.theme.TemaApp
import com.example.a21.ui.theme._21Theme

class MainScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val temaInicial=TemaApp.valueOf(
            intent.getStringExtra("tema") ?: TemaApp.CORAL.name
        )
        val nombreInicial = intent.getStringExtra("nombre") ?: ""

        setContent {
            var tema by remember { mutableStateOf(temaInicial) }
            var nombre by remember { mutableStateOf(nombreInicial) }

            _21Theme(tema = tema) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    screen(
                        tema = tema,
                        onTemaChange = { tema = it },
                        nombre = nombre,
                        onNombreChange = { nombre = it },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun screen(tema: TemaApp, onTemaChange: (TemaApp) -> Unit,nombre: String,
           onNombreChange: (String) -> Unit, modifier: Modifier = Modifier) {
    val context=LocalContext.current
    val nombreListo = nombre.isNotBlank()
    var mostrarTemas by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text("21", fontSize = 32.sp)

        OutlinedTextField(
            value = nombre,
            onValueChange = { if (it.length <= 15) onNombreChange(it) },
            label = { Text("Escribe tu nombre") },
            singleLine = true
        )

        Row(modifier = Modifier.padding(8.dp)) {
            Button(onClick = {
                val destino = Intent(
                    context,
                    ResultsScreen::class.java
                ).apply {
                    putExtra("tema", tema.name)
                }
                context.startActivity(destino)
            }) {
                Text("VER \n SCORE", textAlign = TextAlign.Center)
            }
            Button(onClick = {
                val destino = Intent(
                    context,
                    InstruccionesActivity::class.java
                ).apply {
                    putExtra("tema", tema.name)
                }
                context.startActivity(destino)
            }) {
                Text("VER \n INSTRUCCIONES", textAlign = TextAlign.Center)
            }

        }
        Row(modifier = Modifier.padding(8.dp)) {
            Button(
                onClick = {
                    val destino = Intent(context, SeleccionScreen::class.java).apply {
                        putExtra("tema", tema.name)
                        putExtra("nombre", nombre.trim())
                    }
                    context.startActivity(destino)
                },
                enabled = nombreListo
            ) {
                Text("A \n JUGAR! ", textAlign = TextAlign.Center)
            }
            Button(onClick = { mostrarTemas = !mostrarTemas }) {
                Text("ELEGIR \n TEMA", textAlign = TextAlign.Center)
            }
        }


        if (mostrarTemas) {
            themeSelector(onTemaChange = onTemaChange)
        }
    }
}

@Composable
fun themeSelector(onTemaChange: (TemaApp) -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text("SELECCIONA UN TEMA: ", fontSize = 20.sp)
        Button(onClick = { onTemaChange(TemaApp.CORAL) })  { Text("Coral") }
        Button(onClick = { onTemaChange(TemaApp.CASINO) }) { Text("Casino") }
        Button(onClick = { onTemaChange(TemaApp.OCEANO) }) { Text("Azul") }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _21Theme {
        screen(TemaApp.CORAL,  onTemaChange = {}, "Davi",onNombreChange = {})
    }
}