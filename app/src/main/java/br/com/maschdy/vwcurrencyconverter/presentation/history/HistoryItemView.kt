package br.com.maschdy.vwcurrencyconverter.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import br.com.maschdy.vwcurrencyconverter.domain.model.HistoryItem
import br.com.maschdy.vwcurrencyconverter.presentation.theme.VWCurrencyConverterTheme

@Composable
fun HistoryItemView(
    item: HistoryItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = item.value,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryItemViewPreview() {
    VWCurrencyConverterTheme {
        HistoryItemView(item = HistoryItem(name = "BRL/USD", value = "R$100 - $100"))
    }
}
