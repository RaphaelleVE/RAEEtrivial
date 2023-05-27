package com.example.raeetrivial.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.raeetrivial.R
import com.example.raeetrivial.ui.theme.YellowWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    val viewModel = hiltViewModel<ProfileViewModel>()
    val currentUser = viewModel.currentUserFlow.collectAsState().value

    var isEditingPseudo by remember { mutableStateOf(false) }
    val getCurrentPseudo = viewModel.currentUserFlow.collectAsState().value.pseudo
    var newPseudo by remember { mutableStateOf(getCurrentPseudo) }
    var triedChange by remember { mutableStateOf(false) }
    val tempoPseudo = viewModel.tempoPseudoFlow.collectAsState().value

    LaunchedEffect(key1 = tempoPseudo){
        if(triedChange) {
            isEditingPseudo = !isEditingPseudo
        }
    }
    
    
    Column() {
    if(currentUser.email != ""){
        Column(modifier = Modifier.padding(10.dp)) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.pfp_raee),
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape))
            }
            Text(
                text = "Pseudo",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = YellowWhite,
            )
            Row( modifier = Modifier.fillMaxWidth(),Arrangement.SpaceAround) {
                if(!isEditingPseudo) {
                    Text(
                        text = currentUser.pseudo,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = YellowWhite,
                    )

                }else{
                    TextField(
                        modifier = Modifier
                            .width(200.dp),
                        value = newPseudo,
                        onValueChange = {
                            newPseudo = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = YellowWhite,
                            cursorColor = MaterialTheme.colorScheme.secondary,
                            textColor = Color.Black
                        ),
                        shape = RoundedCornerShape(7.dp),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            imeAction = ImeAction.Next
                        )
                    )
                }
                IconButton(
                    onClick = {
                            if(isEditingPseudo){
                                viewModel.changeUserPseudo(newPseudo)
                                triedChange=true
                            }else {
                                newPseudo = getCurrentPseudo
                                isEditingPseudo = true
                            }
                        },
                    modifier = Modifier
                        .fillMaxWidth()) {
                            Icon(
                            painter =
                                if(!isEditingPseudo) painterResource(id = R.drawable.ic_edit_48px)
                                else painterResource(id = R.drawable.ic_check_circle_48px),
                            contentDescription = "EDIT PSEUDO",
                            tint = YellowWhite)
                        }
            }
            Text(
                text = "Email",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = YellowWhite,
            )
            Text(
                text = currentUser.email,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = YellowWhite,
            )
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
                    color = YellowWhite)
            }
        }
    }
}

