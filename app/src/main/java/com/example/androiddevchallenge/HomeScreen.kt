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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.data.Dog

@Composable
fun HomeScreen(viewModel: MainViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Puppy-Adoption") })
        },
    ) {
        val dogs = viewModel.dogs
        LazyColumn {
            items(dogs, null) { dog: Dog -> DogItem(dog) { viewModel.onItemClick(dog) } }
        }
    }
}

@Composable
private fun DogItem(dog: Dog, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick() }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = dog.imageUrl,
            fontSize = 40.sp,
        )

        Column(Modifier.padding(0.dp, 10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = dog.name,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                AdoptionStatus(dog.adopted)
            }
            Text(
                text = dog.description,
                style = MaterialTheme.typography.body1,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun AdoptionStatus(adopted: Boolean) {
    val statusStr =
        stringResource(id = if (adopted) R.string.adopted else R.string.adoption)

    val backgroundColor =
        if (adopted) MaterialTheme.colors.onSurface.copy(alpha = 0.2f) else MaterialTheme.colors.secondary

    Surface(
        shape = RoundedCornerShape(12.dp, 0.dp, 0.dp, 12.dp),
        color = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(12.dp, 4.dp, 6.dp, 4.dp),
            text = statusStr,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun DogItemPreview() {

    DogItem(
        Dog(
            0,
            "dog1",
            "\uD83D\uDC15\u200D\uD83E\uDDBA",
            "A puppy is a juvenile dog.",
            false
        )
    ) {
    }
}
