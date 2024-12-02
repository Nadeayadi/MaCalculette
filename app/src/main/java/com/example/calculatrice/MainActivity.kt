package com.example.calculatrice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatrice.ui.theme.CalculatriceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // na : Applique le thème de l'application et affiche l'écran de la calculatrice
            CalculatriceTheme {
                CalculatorScreen()
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    // na : Variables pour gérer l'affichage et les calculs
    var displayText by remember { mutableStateOf("0") } // na : Texte affiché à l'écran
    var operand1 by remember { mutableStateOf(0.0) } // na : Premier opérande
    var operator by remember { mutableStateOf("") } // na : Opérateur sélectionné
    var isNewInput by remember { mutableStateOf(true) } // na : Indique si un nouvel input est en cours

    // na : Conteneur principal de l'écran
    Column(
        modifier = Modifier
            .fillMaxSize() // na : Remplit tout l'écran
            .background(Color.Black) // na : Fond noir
            .padding(16.dp), // na : Marge intérieure
        verticalArrangement = Arrangement.SpaceBetween, // na : Espace égal entre les éléments verticaux
        horizontalAlignment = Alignment.CenterHorizontally // na : Alignement horizontal centré
    ) {
        // na : Affichage principal pour les chiffres et résultats
        Text(
            text = displayText, // na : Contenu du texte à afficher
            color = Color.White, // na : Couleur du texte
            fontSize = 32.sp, // na : Taille du texte
            fontWeight = FontWeight.Bold, // na : Poids du texte en gras
            textAlign = TextAlign.End, // na : Alignement à droite
            modifier = Modifier
                .fillMaxWidth() // na : Texte prend toute la largeur disponible
                .padding(16.dp) // na : Marge autour de l'affichage
                .background(Color.Gray) // na : Fond gris pour l'affichage
        )

        // na : Conteneur pour les boutons de la calculatrice
        Column(
            modifier = Modifier.fillMaxWidth(), // na : Les boutons occupent toute la largeur
            verticalArrangement = Arrangement.spacedBy(8.dp) // na : Espacement vertical entre les lignes
        ) {
            // na : Définition des lignes de boutons
            val rows = listOf(
                listOf("7", "8", "9", "/"), // na : Première ligne de boutons
                listOf("4", "5", "6", "x"), // na : Deuxième ligne de boutons
                listOf("1", "2", "3", "-"), // na : Troisième ligne de boutons
                listOf("C", "0", "=", "+")  // na : Quatrième ligne de boutons
            )
            rows.forEach { row ->
                // na : Conteneur horizontal pour chaque ligne de boutons
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // na : Chaque ligne prend toute la largeur
                        .padding(4.dp), // na : Marge entre les lignes
                    horizontalArrangement = Arrangement.SpaceEvenly // na : Espacement égal entre les boutons
                ) {
                    row.forEach { label ->
                        // na : Création de chaque bouton
                        CalculatorButton(label) {
                            // na : Gestion des actions selon le label du bouton
                            when (label) {
                                "C" -> { // na : Réinitialise la calculatrice
                                    displayText = "0"
                                    operand1 = 0.0
                                    operator = ""
                                    isNewInput = true
                                }
                                in listOf("+", "-", "x", "/") -> { // na : Définit l'opérateur
                                    operand1 = displayText.toDouble()
                                    operator = label
                                    isNewInput = true
                                }
                                "=" -> { // na : Effectue le calcul
                                    val operand2 = displayText.toDouble()
                                    displayText = when (operator) {
                                        "+" -> (operand1 + operand2).toString()
                                        "-" -> (operand1 - operand2).toString()
                                        "x" -> (operand1 * operand2).toString()
                                        "/" -> if (operand2 != 0.0) (operand1 / operand2).toString() else "Error" // na : Gère la division par zéro
                                        else -> displayText
                                    }
                                    isNewInput = true
                                }
                                else -> { // na : Ajoute le chiffre ou met à jour l'affichage
                                    displayText = if (isNewInput) label else displayText + label
                                    isNewInput = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    // na : Bouton avec action au clic
    Button(
        onClick = onClick, // na : Action à exécuter au clic
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp) // na : Marge autour du bouton
            .size(width = 64.dp, height = 64.dp) // na : Taille fixe pour chaque bouton
    ) {
        // na : Texte affiché sur le bouton
        Text(
            text = label, // na : Contenu du bouton
            fontSize = 24.sp, // na : Taille du texte
            color = Color.White // na : Couleur du texte
        )
    }
}
