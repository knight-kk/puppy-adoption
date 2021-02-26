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

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.Dog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    enum class Screen { HOME, DETAIL }

    var currentScreen by mutableStateOf(Screen.HOME)
        private set

    private var _currentDog by mutableStateOf<Dog?>(null)

    var adopted by mutableStateOf(false)
        private set

    val currentDog: Dog
        get() = _currentDog!!

    var dogs by mutableStateOf(listOf<Dog>())
        private set

    fun getDogs(context: Context) {
        viewModelScope.launch {
            val plantList: List<Dog> = withContext(Dispatchers.IO) {
                context.applicationContext.assets.open("dogs.json").use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val plantType = object : TypeToken<List<Dog>>() {}.type
                        Gson().fromJson(jsonReader, plantType)
                    }
                }
            }
            dogs = dogs + plantList
        }
    }

    fun onItemClick(dog: Dog) {
        _currentDog = dog
        adopted = dog.adopted
        currentScreen = Screen.DETAIL
    }

    fun adoption() {
        _currentDog?.adopted = true
        adopted = true
    }

    fun onBack() {
        currentScreen = Screen.HOME
    }
}
