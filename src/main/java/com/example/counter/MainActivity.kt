package com.example.counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.counter.ui.theme.CounterTheme
import com.example.counter.viewmodel.CounterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CounterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().systemBarsPadding().verticalScroll(rememberScrollState())
                ) {

                Counter()
                }
            }
        }
    }
}

@Composable
fun Counter(
    viewModel: CounterViewModel = viewModel(),
) {

    val counter by viewModel.counter.collectAsState() // Observe StateFlow

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ViewModel",
            fontWeight = FontWeight.Bold
        )
        Text("Counter : $counter")
        Button(onClick = {
            viewModel.increment()
        }) {
            Text("Increment")
        }
        Button(onClick = {
            viewModel.decrement()
        }) {
            Text("Decrement")
        }
        Button(onClick = { viewModel.reset() }) {
            Text("Reset")
        }

        Spacer(modifier = Modifier.height(30.dp))

    Counter2()
    }
}

@Composable
fun Counter2(){
    var counter by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Without ViewModel",
            fontWeight = FontWeight.Bold
        )
        Text("Counter : $counter")
        Button(onClick = {
            counter++
        }) {
            Text("Increment")
        }
        Button(onClick = {
            counter--
        }) {
            Text("Decrement")
        }
        Button(onClick = { counter=0 }) {
            Text("Reset")
        }
    }

}

