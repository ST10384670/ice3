package com.example.ice3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private var running = mutableStateOf(false)
    private var startTime = mutableStateOf(0L)
    private var category = mutableStateOf("Focus")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DetoxApp()
        }
    }

    @Composable
    fun DetoxApp() {

        var time by remember { mutableStateOf(0L) }

        LaunchedEffect(running.value) {
            while (running.value) {
                time = System.currentTimeMillis() - startTime.value
                delay(1000)
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize()) { padding ->

            Column(modifier = Modifier.padding(padding).padding(16.dp)) {

                Text(
                    text = "Digital Detox Tracker",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (!running.value) {

                    Text("Category")
                    TextField(
                        value = category.value,
                        onValueChange = { category.value = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = {
                        running.value = true
                        startTime.value = System.currentTimeMillis()
                    }) {
                        Text("Start Session")
                    }

                } else {

                    Text("Running Time: ${formatTime(time)}")

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        Button(onClick = {
                            running.value = false
                        }) {
                            Text("End")
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(onClick = {
                            running.value = false
                        }) {
                            Text("Interrupted")
                        }
                    }
                }
            }
        }
    }

    fun formatTime(ms: Long): String {
        val sec = ms / 1000
        val h = sec / 3600
        val m = (sec % 3600) / 60
        val s = sec % 60
        return "%02d:%02d:%02d".format(h, m, s)
    }
}