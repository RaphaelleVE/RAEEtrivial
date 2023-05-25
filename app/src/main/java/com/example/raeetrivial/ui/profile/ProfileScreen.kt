package com.example.raeetrivial.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.raeetrivial.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    val viewModel = hiltViewModel<ProfileViewModel>()
    val currentUser = viewModel.currentUser.collectAsState().value

    Column() {

    if(currentUser != null){
        Column(modifier = Modifier.padding(10.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color.Cyan)
                ) {
                    Image(painter = painterResource(R.drawable.pfp_raee),
                        contentDescription = "profile pic",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape))
                }
                Text(text = currentUser.email, textAlign = TextAlign.Left,
                    fontSize = 25.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp))
            }
        }

        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Score : " + currentUser.score,
                    textAlign = TextAlign.Left,
                    fontSize = 30.sp,
                    color = Color.Black,)
            }
        }
    }

    }
}
