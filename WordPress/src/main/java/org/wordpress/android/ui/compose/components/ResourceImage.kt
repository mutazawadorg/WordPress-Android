package org.wordpress.android.ui.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

/**
 * Image component used to display an image from app resources.
 */
@Suppress("LongParameterList")
@Composable
fun ResourceImage(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center
) {
    val painter = painterResource(imageRes)

    Image(
            painter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            alignment = alignment,
            modifier = modifier
    )
}
