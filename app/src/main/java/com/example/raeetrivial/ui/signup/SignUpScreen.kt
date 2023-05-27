package com.example.raeetrivial.ui.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
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
import com.example.raeetrivial.ui.theme.YellowWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {

    val viewModel = hiltViewModel<SignupViewModel>()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmationPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isTryRegister = viewModel.tryRegisterFlow.collectAsState().value
    val isSuccesRegister = viewModel.succesRegisterFlow.collectAsState().value

    LaunchedEffect(isTryRegister){
        if(isTryRegister) {
            if (isSuccesRegister) {
                navController.navigate(Route.BASE)
            } else {
                Toast.makeText(context, "Inscription échouée", Toast.LENGTH_SHORT).show()
            }
        viewModel.resetIsTryRegister()
        }
    }

    Column (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary)
        .padding(dimensionResource(id = R.dimen.loginPadding)),
    verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.trivial_logo),
            contentDescription = "logo trivial" ,
            Modifier
                .fillMaxWidth()
                .size(135.dp),
            contentScale = ContentScale.Fit,

            )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.blockSpacer))
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.editTextHeight)),
            value = email,
            onValueChange = {
                email = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = YellowWhite,
                cursorColor = MaterialTheme.colorScheme.secondary,
                textColor = Color.Black
            ),
            shape = RoundedCornerShape(7.dp),
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
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.miniSpacer))
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.editTextHeight)),
            value = password,
            onValueChange = {
                password = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = YellowWhite,
                cursorColor = MaterialTheme.colorScheme.secondary,
                textColor = Color.Black
            ),
            label = {
                Text(text = stringResource(R.string.password))
            },
            shape = RoundedCornerShape(7.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.miniSpacer))
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.editTextHeight)),
            value = confirmationPassword,
            onValueChange = {
                confirmationPassword = it
            },
            shape = RoundedCornerShape(7.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = YellowWhite,
                cursorColor = MaterialTheme.colorScheme.secondary,
                textColor = Color.Black
            ),
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

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.blockSpacer))
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
            shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = YellowWhite
            ),
            onClick = {
                if (viewModel.confirmationPasswordCheck(password, confirmationPassword)) {
                    viewModel.signupUser(email, password)
                } else {
                    Toast.makeText(context, "Not the same passwords ! ", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(
                fontSize = (20.sp),
                text = stringResource(id = R.string.signup),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.miniSpacer))
        )
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = YellowWhite
            ),
            shape = RoundedCornerShape(7.dp),
            onClick = {
                navController.navigate(Route.SIGN_IN)
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

