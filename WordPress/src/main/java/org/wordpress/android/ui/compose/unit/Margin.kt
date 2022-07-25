package org.wordpress.android.ui.compose.unit

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("MagicNumber")
sealed class Margin(val value: Dp) {
    object Small : Margin(4.dp)
    object ExtraLarge : Margin(16.dp)
    object Medium : Margin(8.dp)
    object ExtraExtraMediumLarge : Margin(32.dp)
}
