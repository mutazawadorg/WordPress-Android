package org.wordpress.android.ui.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Box UI component with vertical scroll.
 */
@Composable
fun VerticalScrollBox(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.then(Modifier.verticalScroll(rememberScrollState())),
        contentAlignment = alignment,
        content = content
    )
}
