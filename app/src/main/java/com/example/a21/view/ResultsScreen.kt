package com.example.a21.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a21.TinyDB
import com.example.a21.model.Carta
import com.example.a21.model.Palo
import com.example.a21.model.Rango
import com.example.a21.model.RegistroPartida
import com.example.a21.ui.theme.TemaApp
import com.example.a21.ui.theme._21Theme

class ResultsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tema = TemaApp.valueOf(
            intent.getStringExtra("tema") ?: TemaApp.CORAL.name
        )
        val tinyDB = TinyDB(this)
        val registros = tinyDB.getListObject("registros", RegistroPartida::class.java)
            .filterIsInstance<RegistroPartida>()
            .reversed()

        setContent {
            _21Theme(tema = tema) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaResultados(
                        registros = registros,
                        onVolver = { finish() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaResultados(
    registros: List<RegistroPartida>,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "RESULTADOS",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (registros.isEmpty()) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("Todavía no hay partidas jugadas")
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(registros) { registro ->
                    TarjetaResultado(registro)
                }
            }
        }

        Button(onClick = onVolver) {
            Text("VOLVER")
        }
    }
}

@Composable
fun TarjetaResultado(registro: RegistroPartida) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (registro.empate) {
                Text("EMPATE", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Entre ${registro.ganador} y ${registro.perdedor}")
                Text("Mano: ${registro.textoManoGanadora()}")
            } else {
                Text("EL GANADOR FUE:  ${registro.ganador}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Perdedor: ${registro.perdedor}")
                Text("Mano ganadora: ${registro.textoManoGanadora()}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(registro.fechaHora, fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultadosPreview() {
    val ejemplo = listOf(
        RegistroPartida(
            "David", "Crupier",
            listOf(Carta(Rango.AS, Palo.PICAS), Carta(Rango.REY, Palo.CORAZONES)),
            21, "14/10/2026 15:30", false
        ),

    )
    _21Theme {
        PantallaResultados(registros = ejemplo, onVolver = {})
    }
}