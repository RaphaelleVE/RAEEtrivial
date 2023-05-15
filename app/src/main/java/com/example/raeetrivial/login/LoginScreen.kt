package com.example.raeetrivial.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.raeetrivial.R
import com.example.raeetrivial.ui.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
    Column (modifier = Modifier.fillMaxWidth(),
        ){
        TextField(modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange ={
                email = it
            },
            label = {
                Text(text = stringResource(R.string.email))
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        TextField(modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange ={
                password = it
            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Route.BASE)
            }
        ){
            Text(
                text =  stringResource(id = R.string.signin),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Route.SIGNUP)
            }
        ){
            Text(
                text =  stringResource(id = R.string.not_register_signup),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    }
}