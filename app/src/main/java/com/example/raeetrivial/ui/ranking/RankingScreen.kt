package com.example.raeetrivial.ui.ranking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(navController: NavController) {
    // A surface container using the 'background' color from the theme
    Scaffold() {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(10) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier= Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)){
                            Column {
                                Text(text = it.toString())
                                Text(text = "UserName")
                                Text(text="Score : 000000")
                                Text(text = "---------------------------------------------------")
                            }
                        }
                    }
                }

            }
        }
    }
}


