package com.example.a21.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

enum class TemaApp { CORAL, CASINO, OCEANO }

private val EsquemaClaro = lightColorScheme(
    primary = CoralBoton,
    onPrimary = Blanco,
    primaryContainer = CoralClaro,
    onPrimaryContainer = VinoSombra,
    secondary = VinoSombra,
    onSecondary = Blanco,
    secondaryContainer = CremaOscuro,
    onSecondaryContainer = VinoSombra,
    tertiary = DoradoAcento,
    onTertiary = TextoCafe,
    background = CremaFondo,
    onBackground = TextoCafe,
    surface = CremaFondo,
    onSurface = TextoCafe,
    surfaceVariant = CremaOscuro,
    onSurfaceVariant = TextoCafeSuave,
    outline = BordeTierra
)

private val EsquemaCasino = darkColorScheme(
    primary = DoradoBoton,
    onPrimary = TextoSobreOro,
    primaryContainer = DoradoSombra,
    onPrimaryContainer = TextoMarfil,

    secondary = RojoAcento,
    onSecondary = TextoMarfil,
    secondaryContainer = TapeteVariante,
    onSecondaryContainer = TextoMarfil,

    tertiary = TextoMarfilSuave,
    onTertiary = TapeteFondo,

    background = TapeteFondo,
    onBackground = TextoMarfil,

    surface = TapeteSuperficie,
    onSurface = TextoMarfil,
    surfaceVariant = TapeteVariante,
    onSurfaceVariant = TextoMarfilSuave,

    outline = BordeVerde
)

private val EsquemaOceano = lightColorScheme(
    primary = AzulBoton,
    onPrimary = Blanco,
    primaryContainer = AzulClaro,
    onPrimaryContainer = AzulSombra,

    secondary = AzulSombra,
    onSecondary = Blanco,
    secondaryContainer = OceanoSuperficie,
    onSecondaryContainer = AzulSombra,

    tertiary = NaranjaAcento,
    onTertiary = TextoAzulNoche,

    background = OceanoFondo,
    onBackground = TextoAzulNoche,

    surface = OceanoFondo,
    onSurface = TextoAzulNoche,
    surfaceVariant = OceanoSuperficie,
    onSurfaceVariant = TextoAzulSuave,

    outline = BordeAzul
)

@Composable
fun _21Theme(
    tema: TemaApp = TemaApp.CORAL,
    content: @Composable () -> Unit
) {
    val esquema = when (tema) {
        TemaApp.CORAL  -> EsquemaClaro
        TemaApp.CASINO -> EsquemaCasino
        TemaApp.OCEANO -> EsquemaOceano
    }

    MaterialTheme(
        colorScheme = esquema,
        typography = Typography,
        content = content
    )
}