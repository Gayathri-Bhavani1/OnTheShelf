package com.paltech.ontheshelf.presentation.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.presentation.common.OnShelfButton
import com.paltech.ontheshelf.presentation.common.OnShelfMyTextField
import com.paltech.ontheshelf.presentation.navigation.Route

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header Section
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.blue))
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.splash_icon),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "OnTheShelf.",
                    style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W400
                    ),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            Text(
                text = "Fresh Goods Delivered to Your DoorStep!",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(end = 60.dp, top = 10.dp, bottom = 50.dp, start = 12.dp),
                style = TextStyle(
                    color = colorResource(id = R.color.white),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                softWrap = true,
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        // Input Fields Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            OnShelfMyTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.email.value,
                hintText = "Email",
                onValueChange = { viewModel.onEmailChange(it) }
            )
            Spacer(modifier = Modifier.height(20.dp))

            OnShelfMyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                value = viewModel.password.value,
                hintText = "Password",
                onValueChange = { viewModel.onPasswordChange(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Handle login status
            when (viewModel.loginStatus.value) {
                is LoginViewModel.LoginStatus.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                is LoginViewModel.LoginStatus.Success -> {
                    navController.navigate(Route.MainNavigation.route) {
                        popUpTo("login") { inclusive = true }
                    }
                }
                is LoginViewModel.LoginStatus.Error -> {
                    val errorMessage =
                        (viewModel.loginStatus.value as LoginViewModel.LoginStatus.Error).message
                    Text(errorMessage, color = Color.Red, modifier = Modifier.padding(10.dp))
                }
                else -> Unit
            }

            // Forgot Password Text
            Text(
                text = "Forgot Password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 10.dp),
                color = colorResource(id = R.color.blue),
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Login Button
            OnShelfButton(
                color = colorResource(id = R.color.green),
                onClick = {
                    viewModel.onLogin()
                          },
           text = "Login",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Create Account Text
            Text(
                text = "Create Account",
                modifier = Modifier.clickable { /* Handle create account action */ },
                color = colorResource(id = R.color.blue),
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}
