package com.example.testapp.presentation.cards.crypto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.testapp.R
import com.example.testapp.domain.models.CryptoData

@Composable
fun CryptoCard(vm: ViewModel?) {
    if (vm == null || vm !is CryptoCardViewModel) throw Exception()
    val state = vm.state.value

    Box(modifier = Modifier.height(192.dp), contentAlignment = Alignment.Center) {
        if (state.error) {
            Text(text = "Ошибка. Повторная попытка...")
        } else if (state.loading) {
            CircularProgressIndicator()
        } else if (state.info.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                for (crypto in state.info) {
                    Item(crypto)
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun Item(crypto: CryptoData) {
    Box(
        modifier = Modifier
            .width(112.dp)
            .padding(4.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(), elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = crypto.name,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    fontSize = 10.sp
                )
                AsyncImage(
                    modifier = Modifier.padding(bottom = 16.dp),
                    model = crypto.imageUrl,
                    contentDescription = "image"
                )
                Text(
                    text = String.format("%.4f", crypto.price).removeSuffix("0")
                        .removeSuffix("0") + " $",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = String.format("%.4f", crypto.change24H).removeSuffix("0")
                            .removeSuffix("0"),
                        color = if (crypto.change24H < 0) Color(0xFFFF2727) else Color(
                            0xFF36DD0D
                        ),
                        fontSize = 12.sp
                    )
                    Image(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = if (crypto.change24H < 0) R.drawable.arrow_down else R.drawable.arrow_up),
                        contentDescription = "Arrow"
                    )
                }
            }
        }
    }
}