package com.hdesrosiers.composekeyboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// https://betterprogramming.pub/android-keyboard-handling-using-jetpack-compose-c478f7afaae0

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      KeyboardSample()
    }
  }
}

@Composable
fun KeyboardSample(){
  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 16.dp, end = 16.dp),

    ) {

    var name by rememberSaveable { mutableStateOf("") }
    val updateName = { _name : String ->
      name = _name
    }

    var amount by rememberSaveable { mutableStateOf("") }
    val updateAmount = { _amount : String ->
      amount = _amount
    }

    TextFieldsToExperiment(
      name = name,
      updateName = updateName,
      amount = amount,
      updateAmount = updateAmount
    )

  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldsToExperiment(
  name : String,
  updateName : (String) -> Unit,
  amount : String,
  updateAmount : (String) -> Unit
){
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
      value = name,
      onValueChange = updateName ,
      label = { Text("Name") },
      placeholder = { Text(text = "Name") },
      singleLine = true,
      keyboardOptions = KeyboardOptions.Default.copy(
        capitalization = KeyboardCapitalization.Words,
        autoCorrect = false,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
      ),
      keyboardActions = KeyboardActions(onNext = {
        focusManager.moveFocus(FocusDirection.Down)
      }),
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 6.dp, start = 0.dp, end = 0.dp, bottom = 6.dp),
    )

    OutlinedTextField(
      value = amount,
      onValueChange = updateAmount ,
      label = { Text("Amount") },
      placeholder = { Text(text = "Amount") },
      singleLine = true,
      keyboardOptions = KeyboardOptions.Default.copy(
        capitalization = KeyboardCapitalization.Sentences,
        autoCorrect = true,
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done),
      keyboardActions = KeyboardActions(onDone = {
        focusManager.clearFocus()
      }),
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 6.dp, start = 0.dp, end = 0.dp, bottom = 6.dp),
    )

    val keyboardController = LocalSoftwareKeyboardController.current //experimental api

    Box(modifier = Modifier
      .fillMaxSize()
      .clickable {
        keyboardController?.hide()
        focusManager.clearFocus()
      }) {

    }
  }
}