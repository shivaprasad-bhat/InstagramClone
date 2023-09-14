package me.svbneelmane.instagramclone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.svbneelmane.instagramclone.DestinationScreen
import me.svbneelmane.instagramclone.viewmodels.InstaViewModel
import me.svbneelmane.instagramclone.R
import me.svbneelmane.instagramclone.core.utils.CustomSpinner
import me.svbneelmane.instagramclone.core.utils.CustomTextField
import me.svbneelmane.instagramclone.core.utils.navigateTo

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: InstaViewModel
) {
    val focus = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emailState = remember {
                mutableStateOf(TextFieldValue())
            }
            val passwordState = remember {
                mutableStateOf(TextFieldValue())
            }

            Image(
                painter = painterResource(id = R.drawable.ig_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 100.dp)
                    .padding(8.dp)
            )

            Text(
                text = "Login",
                modifier = Modifier.padding(8.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )

            CustomTextField(modifier = Modifier, state = emailState, labelText = "Email")

            CustomTextField(
                modifier = Modifier,
                state = passwordState,
                labelText = "Password",
                isPasswordField = true
            )

            Button(onClick = {
                focus.clearFocus(force = true)
            }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Login")
            }

            Text(
                text = "New Here? Go to signup ->",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController, DestinationScreen.Signup)
                    },
                textDecoration = TextDecoration.Underline
            )

        }

        val isLoading = viewModel.inProgress.value
        if (isLoading) {
            CustomSpinner()
        }
    }


}