package org.wordpress.android.ui.qrcodeauth.compose.state

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.wordpress.android.R
import org.wordpress.android.ui.compose.components.ResourceImage
import org.wordpress.android.ui.compose.theme.AppTheme
import org.wordpress.android.ui.compose.unit.Margin
import org.wordpress.android.ui.compose.utils.uiStringText
import org.wordpress.android.ui.qrcodeauth.QRCodeAuthUiState
import org.wordpress.android.ui.qrcodeauth.QRCodeAuthUiState.ActionButton.ErrorPrimaryActionButton
import org.wordpress.android.ui.qrcodeauth.QRCodeAuthUiState.ActionButton.ErrorSecondaryActionButton
import org.wordpress.android.ui.qrcodeauth.compose.components.PrimaryButton
import org.wordpress.android.ui.qrcodeauth.compose.components.SecondaryButton
import org.wordpress.android.ui.qrcodeauth.compose.components.Subtitle
import org.wordpress.android.ui.qrcodeauth.compose.components.Title

@Composable
fun ErrorState(uiState: QRCodeAuthUiState.Error) = with(uiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResourceImage(
            modifier = Modifier
                .padding(vertical = Margin.ExtraLarge.value)
                .wrapContentHeight()
                .wrapContentWidth(),
            imageRes = image,
            contentDescription = stringResource(R.string.qrcode_auth_flow_error_content_description),
        )
        Title(text = uiStringText(title))
        Subtitle(text = uiStringText(subtitle))
        primaryActionButton?.let { actionButton ->
            if (actionButton.isVisible) {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = Margin.Small.value,
                            horizontal = Margin.ExtraExtraMediumLarge.value,
                        ),
                    text = uiStringText(actionButton.label),
                    onClick = { actionButton.clickAction.invoke() }
                )
            }
        }
        secondaryActionButton?.let { actionButton ->
            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Margin.Small.value,
                        horizontal = Margin.ExtraExtraMediumLarge.value,
                    ),
                text = uiStringText(actionButton.label),
                onClick = { actionButton.clickAction.invoke() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorStatePreview() {
    AppTheme {
        val state = QRCodeAuthUiState.Error.InvalidData(
            primaryActionButton = ErrorPrimaryActionButton {},
            secondaryActionButton = ErrorSecondaryActionButton {},
        )
        ErrorState(state)
    }
}
