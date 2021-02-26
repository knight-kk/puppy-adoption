/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Dog
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun DogDetailScreen(viewModel: MainViewModel) {
    DogDetailScreen(
        dog = viewModel.currentDog,
        adopted = viewModel.adopted,
        onBack = viewModel::onBack,
        onAdoption = viewModel::adoption
    )
}

@ExperimentalAnimationApi
@Composable
private fun DogDetailScreen(
    dog: Dog,
    adopted: Boolean,
    onBack: () -> Unit,
    onAdoption: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val state = rememberScrollState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colors.surface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(!adopted && !state.isScrollInProgress) {
                FloatingActionButton(
                    onClick = {
                        onAdoption()
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("${dog.name} adopted")
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "add",
                        tint = MaterialTheme.colors.surface
                    )
                }
            }
        },
    ) {

        coroutineScope.launch {

            Log.i("TAG", "DogDetailScreen: ${state.value}")
        }

        Column(
            modifier = Modifier.verticalScroll(state),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Log.i("TAG", "DogDetailScreen: ${state.value}")
            Text(
                text = dog.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
                    .padding(0.dp, 20.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h1,
            )

            Text(text = dog.name, style = MaterialTheme.typography.h3)
            Text(
                text = stringResource(id = R.string.puppy_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun DogDetailPreview() {

    DogDetailScreen(
        Dog(
            0,
            "dog1",
            "\uD83D\uDC15\u200D\uD83E\uDDBA",
            "A puppy is a juvenile dog.",
            false
        ),
        false, {}, {}
    )
}
