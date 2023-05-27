package com.example.raeetrivial.ui.ranking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.raeetrivial.R
import com.example.raeetrivial.ui.theme.YellowWhite


@Composable
fun RankingScreen() {

    val rankingViewModel = hiltViewModel<RankingViewModel>()
    val userlist = rankingViewModel.rankingFlow.collectAsState().value.sortedByDescending { it.score }

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
                items(userlist.size){ index ->
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimensionResource(id = R.dimen.bigButtonHeight)),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(dimensionResource(id = R.dimen.miniSpacer)),
                                fontSize = (25.sp),
                                text = (index+1).toString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(dimensionResource(id = R.dimen.miniSpacer)),
                                fontSize = (20.sp),
                                textAlign = TextAlign.Center,
                                text = userlist[index].pseudo
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(id = R.dimen.miniSpacer)),
                                fontSize = (17.sp),
                                text = userlist[index].score.toString(),
                                textAlign = TextAlign.End
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 2.dp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    )
}




