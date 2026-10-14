package com.example.a21.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a21.model.Carta
import com.example.a21.model.Palo
import com.example.a21.model.Rango
import com.example.a21.ui.theme._21Theme

private val FondoCarta = Color.White
private val RojoPalo = Color(0xFFC62828)
private val NegroPalo = Color(0xFF1B1B1B)

@Composable
fun CartaGUI(
    carta: Carta,
    bocaArriba: Boolean = true,
    modifier: Modifier = Modifier.width(80.dp)
) {
    val forma = RoundedCornerShape(8.dp)

    if (bocaArriba) {
        val colorPalo = if (carta.palo.esRojo) RojoPalo else NegroPalo

        Box(
            modifier = modifier
                .aspectRatio(5f / 7f)
                .background(FondoCarta, forma)
                .border(1.dp, Color.Gray, forma)
                .padding(6.dp)
        ) {
            // Esquina superior izquierda
            EsquinaCarta(
                rango = carta.rango.etiqueta,
                simbolo = carta.palo.simbolo,
                color = colorPalo,
                modifier = Modifier.align(Alignment.TopStart)
            )

            // Palo grande al centro
            Text(
                text = carta.palo.simbolo,
                color = colorPalo,
                fontSize = 36.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            EsquinaCarta(
                rango = carta.rango.etiqueta,
                simbolo = carta.palo.simbolo,
                color = colorPalo,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .rotate(180f)
            )
        }
    } else {
        // Reverso: usa los colores del tema
        Box(
            modifier = modifier
                .aspectRatio(5f / 7f)
                .background(MaterialTheme.colorScheme.primary, forma)
                .padding(6.dp)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.onPrimary,
                    RoundedCornerShape(4.dp)
                )
        )
    }
}

@Composable
private fun EsquinaCarta(
    rango: String,
    simbolo: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = rango,
            color = color,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CartaGUIPreview() {
    _21Theme {

        CartaGUI(Carta(Rango.AS, Palo.PICAS))

    }
}