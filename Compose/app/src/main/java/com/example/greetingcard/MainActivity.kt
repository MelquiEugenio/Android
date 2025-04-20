package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greetingcard.ui.theme.GreetingCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingCardTheme {
                Greeting(
                    from = stringResource(R.string.my_nick_name),
                )
            }
        }
    }
}

@Composable
fun Greeting(from: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.androidparty),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            alpha = 0.5F,
        )
        LazyColumn(contentPadding = PaddingValues(top = 50.dp)) {
            items(4) { _ ->
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Happy coding!",
                        textAlign = TextAlign.Center,
                        fontSize = 100.sp,
                        lineHeight = 100.sp,
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 40.dp, top = 10.dp)
                            .background(color = Color.White)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        text = "From $from!",
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp,
                        lineHeight = 36.sp,
                    )
                    Spacer(modifier = Modifier.padding(bottom = 50.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun GreetingPreview() {
    GreetingCardTheme {
        Greeting(stringResource(R.string.my_nick_name))
    }
}