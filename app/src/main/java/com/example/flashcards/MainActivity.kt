package com.example.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                QuizScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen() {
    // Choix touché (null = rien de choisi)
    var selected by remember { mutableStateOf<Int?>(null) }

    // Les choix sont-ils affichés ? (tâche 6)
    var showChoices by remember { mutableStateOf(true) }

    val options = listOf("Bill Clinton", "George H. W. Bush", "Barack Obama")
    val correctIndex = 2 // Barack Obama

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flashcards") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3F51B5),
                    titleContentColor = Color.White
                ),
                actions = {
                    // Bouton œil : affiche ou masque les choix
                    TextButton(onClick = { showChoices = !showChoices }) {
                        Text(
                            text = if (showChoices) "🙈" else "👁",
                            fontSize = 22.sp,
                            color = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .widthIn(max = 600.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Carte de la question
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFBBDEFB), RoundedCornerShape(32.dp))
                    .border(4.dp, Color(0xFF0D47A1), RoundedCornerShape(32.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Who is the 44th President of the United States?",
                    fontSize = 26.sp,
                    color = Color(0xFF455A64),
                    textAlign = TextAlign.Center
                )
            }

            // Choix de réponse : affichés seulement si showChoices est vrai
            if (showChoices) {
                options.forEachIndexed { index, option ->
                    val backgroundColor = when {
                        selected == null -> Color(0xFFFFDAB9)       // avant de répondre : pêche
                        index == correctIndex -> Color(0xFF3CB371)  // bonne réponse : vert
                        index == selected -> Color(0xFFF08080)      // mauvaise réponse touchée : rouge
                        else -> Color(0xFFFFDAB9)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor, RoundedCornerShape(8.dp))
                            .clickable(enabled = selected == null) { selected = index }
                            .padding(vertical = 16.dp, horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            fontSize = 20.sp,
                            color = Color(0xFF3F51B5)
                        )
                    }
                }
            }

            // Message de résultat
            if (selected != null) {
                Text(
                    text = if (selected == correctIndex) "Good !" else "Wrong",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }
        }
    }
}