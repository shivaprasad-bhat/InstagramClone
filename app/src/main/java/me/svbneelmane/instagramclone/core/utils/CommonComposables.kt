package me.svbneelmane.instagramclone.core.utils

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import me.svbneelmane.instagramclone.viewmodels.InstaViewModel

@Composable
fun NotificationMessage(viewModel: InstaViewModel) {
    val notificationState = viewModel.popupNotification.value

    val notifyMessage = notificationState?.getContentOrNull()

    if (notifyMessage != null) {
        Toast.makeText(LocalContext.current, notifyMessage, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun CustomSpinner() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false, onClick = { })
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier,
    state: MutableState<TextFieldValue>,
    labelText: String,
    isPasswordField: Boolean = false
) {
    OutlinedTextField(
        value = state.value, onValueChange = {
            state.value = it
        },
        modifier = modifier.padding(8.dp),
        label = {
            Text(text = labelText)
        },
        visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None
    )
}
