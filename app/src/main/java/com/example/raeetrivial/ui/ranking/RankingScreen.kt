package com.example.raeetrivial.ui.ranking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.raeetrivial.R
import com.example.raeetrivial.ui.theme.MainDarkBleue
import com.example.raeetrivial.ui.theme.YellowWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(navController: NavController) {
    // A surface container using the 'background' color from the theme
    Card (
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.loginPadding)),
        colors = CardDefaults.cardColors(
            containerColor = YellowWhite,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
        content = {
        LazyColumn() {
            items(10) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier= Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)){
                            Text(text = (it + 1).toString())
                            Text(text = "UserName")
                            Text(text="Score : 000000")
                        }
                    }
                }

            }
        }
        })
    }



