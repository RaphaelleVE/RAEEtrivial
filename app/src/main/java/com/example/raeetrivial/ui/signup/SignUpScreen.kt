package com.example.raeetrivial.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.raeetrivial.R
import com.example.raeetrivial.ui.Route


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {

    val viewModel = hiltViewModel<SignupViewModel>()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authResource = viewModel.signupFlow.collectAsState()

    Column (modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            painter = painterResource(id = R.drawable.img_trivial_logo),
            contentDescription = "logo trivial" ,
            Modifier.fillMaxWidth(),
            )
            Column(Modifier.fillMaxWidth()
                .padding(30.dp),
                verticalArrangement = Arrangement.SpaceEvenly
                ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = {
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
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = {
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
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = {
                        Text(text = stringResource(R.string.confirm_password))
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )
            }
            Column(Modifier.fillMaxWidth()
                .padding(30.dp),
                verticalArrangement = Arrangement.Bottom) {
                Button(modifier = Modifier.fillMaxWidth().padding(5.dp).height(60.dp),
                    shape = RoundedCornerShape(7.dp),
                    onClick = {
                        viewModel.signupUser(email, password)
                    }
                ) {
                    Text(
                        fontSize = (20.sp),
                        text = stringResource(id = R.string.signup),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Button(modifier = Modifier.fillMaxWidth().padding(5.dp).height(60.dp),
                    shape = RoundedCornerShape(7.dp),
                    onClick = {
                        navController.navigate(Route.LOGIN)
                    }
                ) {
                    Text(
                        fontSize = (20.sp),
                        text = stringResource(id = R.string.already_signup_login),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
    }
}